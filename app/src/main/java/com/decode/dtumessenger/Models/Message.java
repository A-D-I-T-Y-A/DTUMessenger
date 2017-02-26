package com.decode.dtumessenger.Models;

/**
 * Created by aanyajindal on 25/02/17.
 */

public class Message {

    int msg_id, chat_id, user_id;
    String content, time;

    public Message() {
    }

    public Message(int msg_id, int chat_id, int user_id, String content, String time) {
        this.msg_id = msg_id;
        this.chat_id = chat_id;
        this.user_id = user_id;
        this.content = content;
        this.time = time;
    }

    public int getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }

    public int getChat_id() {
        return chat_id;
    }

    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrlParams(){
        String params = "chat_id="+Integer.toString(chat_id)+"&user_id="+Integer.toString(user_id)
                +"&content="+content+"&time="+time;
        return params;
    }
}
