package com.huaban.analysis.jieba;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class WordDictionary {

    private static final Logger LOGGER = LoggerFactory.getLogger(WordDictionary.class);
    //并发安全机制，可见性和内存屏障
    private static volatile WordDictionary singleton;
    private static final String MAIN_DICT = "/dict.txt";
    private static String USER_DICT_SUFFIX = ".dict";

    //可以适当调整初始容量和加载因子
    public final Map<String, Double> freqs = new ConcurrentHashMap<>();
    /**
     * 缓存当前总词库
     * <pre>
     *      [
     *      "1懂":1.0
     *      "日志系统":3.0
     *      ]
     *  </pre>
     */
    //可以适当调整初始容量和加载因子
    public final Map<String, Double> dictMap = new ConcurrentHashMap<>();

    public final Set<String> loadedPath = new HashSet<String>();
    private static Double minFreq = Double.MAX_VALUE;
    private static Double total = 0.0;
    private static DictSegment _dict;

    static {

    }

    public void load() {

        this.loadDict(MAIN_DICT);
    }

    private WordDictionary() {

    }

    public static WordDictionary getInstance() {
        if (singleton == null) {
            synchronized (WordDictionary.class) {
                if (singleton == null) {
                    singleton = new WordDictionary();
                    return singleton;
                }
            }
        }
        return singleton;
    }


    /**
     * 枚举单例
     */
    public static final WordDictionary _getInstance() {

        return WordDictionaryInstance.INSTANCE.getInstance();
    }

    private enum WordDictionaryInstance {
        INSTANCE;
        private WordDictionary wordDictionary;

        WordDictionaryInstance() {
            this.wordDictionary = new WordDictionary();
        }

        public WordDictionary getInstance() {
            return this.wordDictionary;
        }
    }

    /**
     * for ES to initialize the user dictionary.
     *
     * @param configFile
     */
    public void init(Path configFile) {
        String abspath = configFile.toAbsolutePath().toString();
        System.out.println("initialize user dictionary:" + abspath);
        synchronized (WordDictionary.class) {
            if (loadedPath.contains(abspath))
                return;

            DirectoryStream<Path> stream;
            try {
                stream = Files.newDirectoryStream(configFile, String.format(Locale.getDefault(), "*%s", USER_DICT_SUFFIX));
                for (Path path : stream) {
                    System.err.println(String.format(Locale.getDefault(), "loading dict %s", path.toString()));
                    singleton.loadUserDict(path);
                }
                loadedPath.add(abspath);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
                System.err.println(String.format(Locale.getDefault(), "%s: load user dict failure!", configFile.toString()));
            }
        }
    }

    public void init(String[] paths) {
        synchronized (WordDictionary.class) {
            for (String path : paths) {
                if (!loadedPath.contains(path)) {
                    try {
                        System.out.println("initialize user dictionary: " + path);
                        singleton.loadUserDict(path);
                        loadedPath.add(path);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        System.err.println(String.format(Locale.getDefault(), "%s: load user dict failure!", path));
                    }
                }
            }
        }
    }

    /**
     * let user just use their own dict instead of the default dict
     */
    public void resetDict() {
        _dict = new DictSegment((char) 0);
        freqs.clear();
    }

    public void loadDict(String path) {
        if (_dict == null) {
            _dict = new DictSegment((char) 0);
        }
        InputStream is = this.getClass().getResourceAsStream(path);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

            long s = System.currentTimeMillis();
            while (br.ready()) {
                String line = br.readLine();
                String[] tokens = line.split("[\t ]+");

                if (tokens.length < 2)
                    continue;

                String word = tokens[0];
                double freq = Double.valueOf(tokens[1]);
                total += freq;
                word = addWord(word);
                freqs.put(word, freq);

                //原词典加入总词库，后续每次动态更新词典，都要重新计算词频系数
                dictMap.put(word, freq);
            }
            System.out.println("load the thesaurus , the current size = " + dictMap.size());
            // normalize
            for (Entry<String, Double> entry : freqs.entrySet()) {
                entry.setValue((Math.log(entry.getValue() / total)));
                minFreq = Math.min(entry.getValue(), minFreq);
            }
            System.out.println(String.format(Locale.getDefault(), "main dict load finished, time elapsed %d ms",
                    System.currentTimeMillis() - s));
        } catch (IOException e) {
            System.err.println(String.format(Locale.getDefault(), "%s load failure!", MAIN_DICT));
        } finally {
            try {
                if (null != is)
                    is.close();
            } catch (IOException e) {
                System.err.println(String.format(Locale.getDefault(), "%s close failure!", MAIN_DICT));
            }
        }
    }

    /*
     * 修改该方法为public，使其可以支持动态向字典表中添加单词。
     */
    public String addWord(String word) {
        if (null != word && !"".equals(word.trim())) {
            String key = word.trim().toLowerCase(Locale.getDefault());
            _dict.fillSegment(key.toCharArray());
            return key;
        } else
            return null;
    }

    /*
     * 新增停用方法，使其可以动态停用
     */
    public void disableWord(String word) {
        if (null == word || "".equals(word.trim())) {
            return;
        }
        String key = word.trim().toLowerCase(Locale.getDefault());
        _dict.disableSegment(key.toCharArray());

        //刷新词典和词频系数
        Double freq = dictMap.get(word);
        if (freq == null) {
            return;
        }
        dictMap.remove(word);
        total -= freq;
        freqs.remove(word);
        for (Entry<String, Double> entry : dictMap.entrySet()) {
            double value = Math.log(entry.getValue() / total);
            freqs.put(entry.getKey(), value);
            minFreq = Math.min(value, minFreq);
        }
        LOGGER.debug("disable the thesaurus , the current size = " + freqs.size());
    }

    /**
     * 动态添加热词，刷新freqs词频和词频系数。ep:"地下城 3"
     */
    public void refresh(String keyword) {

        if (_dict == null) {
            _dict = new DictSegment((char) 0);
        }
        String[] tokens = keyword.split("[\t ]+");
        if (tokens.length < 2)
            return;

        //添加热词，每次重新计算total（词频总数）
        String word = tokens[0];
        Double freq = Double.valueOf(tokens[1]);
        total += freq;
        word = addWord(word);
        if (StringUtils.isBlank(word) || freq == null) {
            return;
        }
        freqs.put(word, freq);
        dictMap.put(word, freq);//动态更新总词库

        // 新增热词之后，刷新freqs的词频系数
        for (Entry<String, Double> entry : dictMap.entrySet()) {
            //新的词频系数
            double value = Math.log(entry.getValue() / total);
            freqs.put(entry.getKey(), value);
            minFreq = Math.min(value, minFreq);
        }
        LOGGER.debug("refresh the thesaurus , the current size = " + freqs.size());
    }

    public void loadUserDict(Path userDict) {
        loadUserDict(userDict, StandardCharsets.UTF_8);
    }

    public void loadUserDict(String userDictPath) {
        loadUserDict(userDictPath, StandardCharsets.UTF_8);
    }

    public void loadUserDict(Path userDict, Charset charset) {
        try {
            BufferedReader br = Files.newBufferedReader(userDict, charset);
            long s = System.currentTimeMillis();
            int count = 0;
            while (br.ready()) {
                String line = br.readLine();
                String[] tokens = line.split("[\t ]+");

                if (tokens.length < 1) {
                    // Ignore empty line
                    continue;
                }

                String word = tokens[0];

                double freq = 3.0d;
                if (tokens.length == 2)
                    freq = Double.valueOf(tokens[1]);
                word = addWord(word);
                freqs.put(word, Math.log(freq / total));
                count++;
            }
            System.out.println(String.format(Locale.getDefault(), "user dict %s load finished, tot words:%d, time elapsed:%dms", userDict.toString(), count, System.currentTimeMillis() - s));
            br.close();
        } catch (IOException e) {
            System.err.println(String.format(Locale.getDefault(), "%s: load user dict failure!", userDict.toString()));
        }
    }

    public void loadUserDict(String userDictPath, Charset charset) {
        InputStream is = this.getClass().getResourceAsStream(userDictPath);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, charset));

            long s = System.currentTimeMillis();
            int count = 0;
            while (br.ready()) {
                String line = br.readLine();
                String[] tokens = line.split("[\t ]+");

                if (tokens.length < 1) {
                    // Ignore empty line
                    continue;
                }

                String word = tokens[0];

                double freq = 3.0d;
                if (tokens.length == 2)
                    freq = Double.valueOf(tokens[1]);
                word = addWord(word);
                freqs.put(word, Math.log(freq / total));
                count++;
            }
            System.out.println(String.format(Locale.getDefault(), "user dict %s load finished, tot words:%d, time elapsed:%dms", userDictPath, count, System.currentTimeMillis() - s));
            br.close();
        } catch (IOException e) {
            System.err.println(String.format(Locale.getDefault(), "%s: load user dict failure!", userDictPath));
        }
    }

    public DictSegment getTrie() {
        return this._dict;
    }


    public boolean containsWord(String word) {
        return freqs.containsKey(word);
    }


    public Double getFreq(String key) {
        if (containsWord(key))
            return freqs.get(key);
        else
            return minFreq;
    }
}
