package com.zendesk.app.zendeskticketviewer.model;

import lombok.ToString;
import lombok.Data;

import java.util.List;

@ToString
@Data
public class TicketViewerResponse {

    private String next_page;
    private List<Tickets> tickets;
    private Long count;
    private String previous_page;

}
