package com.example.cuizehui.smartschool.entity;

/**
 * Created by cuizehui on 2016/9/25at ${time}.
 */
public class AskAnswer {
    /**
     * rc : 0
     * operation : ANSWER
     * service : baike
     * answer : {"text":"周杰伦作曲，方文山作词，周杰伦演唱的一首中国风歌曲。属于2007年周杰伦制作音乐专辑《我很忙》其中的一首歌曲。","type":"T"}
     * text : 青花瓷。
     */

    private int rc;
    private String operation;
    private String service;
    /**
     * text : 周杰伦作曲，方文山作词，周杰伦演唱的一首中国风歌曲。属于2007年周杰伦制作音乐专辑《我很忙》其中的一首歌曲。
     * type : T
     */

    private AnswerBean answer;
    private String text;

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public AnswerBean getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerBean answer) {
        this.answer = answer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static class AnswerBean {
        private String text;
        private String type;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
