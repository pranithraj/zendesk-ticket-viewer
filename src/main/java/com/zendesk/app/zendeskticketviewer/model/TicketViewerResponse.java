package com.zendesk.app.zendeskticketviewer.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TicketViewerResponse {

    private String next_page;
    private Long count;
    private List<Groups> groups;
    private String previous_page;


}