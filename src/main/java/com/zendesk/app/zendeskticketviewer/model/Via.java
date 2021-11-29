package com.zendesk.app.zendeskticketviewer.model;

import lombok.ToString;
import lombok.Data;
@ToString
@Data
public class Via {

    private String channel;
    private Source source;

}