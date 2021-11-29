package com.zendesk.app.zendeskticketviewer.model;

import lombok.ToString;
import lombok.Data;

@ToString
@Data
public class Source {

    private String rel;
    private String from;
    private String to;

}