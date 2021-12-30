package com.huaban.analysis.jieba;

public class SegToken {
    public String word;

    public int startOffset;

    public int endOffset;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(int startOffset) {
        this.startOffset = startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }

    public void setEndOffset(int endOffset) {
        this.endOffset = endOffset;
    }

    public SegToken(String word, int startOffset, int endOffset) {
        this.word = word;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }


    @Override
    public String toString() {
        return "[" + word + ", " + startOffset + ", " + endOffset + "]";
    }

}
