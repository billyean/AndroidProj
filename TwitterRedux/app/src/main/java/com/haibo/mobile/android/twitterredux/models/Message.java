/*
 * Copyright (c) [2017] [Haibo(Tristan) Yan]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.haibo.mobile.android.twitterredux.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Haibo(Tristan) Yan on 10/8/17.
 */

public class Message {
    private Long mid;

    private String text;

    private User recipient;

    private User sender;

    private String createdAt;

    public static Message fromJSON(JSONObject jsonObject) throws JSONException {
        Message message = new Message();

        message.mid = jsonObject.getLong("id");
        message.sender = User.fromJSON(jsonObject.getJSONObject("sender"));
        message.recipient = User.fromJSON(jsonObject.getJSONObject("recipient"));

        Log.i("profileImageUrl", message.recipient.getProfileImageUrl());
        Log.i("name", message.recipient.getName());
        Log.i("screenName", message.recipient.getScreenName());
        message.text = jsonObject.getString("text");
        message.createdAt = jsonObject.getString("created_at");
        return message;
    }

    public static List<Message> fromJSONArray(JSONArray jsonArray) throws JSONException {
        Log.i("INFO", jsonArray.toString());
        List<Message> messages = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            messages.add(fromJSON(jsonArray.getJSONObject(i)));
        }
        return messages;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTimeBefore() {
        DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
        try {
            Date createdDate = df.parse(createdAt);
            long diff = (Calendar.getInstance().getTime().getTime() - createdDate.getTime()) / 1000;

            if (diff < 60) {
                return String.format("%d s", diff);
            }

            diff /= 60;

            if (diff < 60) {
                return String.format("%d m", diff);
            }

            diff /= 60;

            if (diff < 24) {
                return String.format("%d h", diff);
            }

            diff /= 24;

            if (diff < 30) {
                return String.format("%d d", diff);
            }

            diff /= 30;
            return String.format("%d m", diff);

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
