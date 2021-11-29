package com.zendesk.app.zendeskticketviewer.model;

import lombok.ToString;
import lombok.Data;
@ToString
@Data
public class Tickets {

    private String subject;
    private String created_at;
    private String description;
    private String external_id;
    private String type;
    //    private Via via;
    private Boolean allow_attachments;
    private String updated_at;
    private String problem_id;
    private String due_at;
    private Long id;
    private Long assignee_id;
    private String raw_subject;
    private String forum_topic_id;
    private Boolean allow_channelback;
    private String satisfaction_rating;
    private Long submitter_id;
    private String priority;
    private String url;
    private Long brand_id;
    private Long ticket_form_id;
    private Long group_id;
    private Long organization_id;
    private String recipient;
    private Boolean is_public;
    private Boolean has_incidents;
    private String status;
    private Long requester_id;

    // Converter method
    public static String convertToHTML(TicketViewerResponse stuffs) {
        if (stuffs != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("<ul>\n");
            for (Tickets cd : stuffs.getTickets()) {
                sb.append("<li>Ticket with subject \"" + cd.getSubject() + "\" opened by \"" + cd.getId() + "\" \n");
                sb.append("\n");
            }
            double paginationNumber = 25.0;
            int n = (int) Math.ceil(stuffs.getCount() / paginationNumber);
            sb.append("<br><div style = \"padding-right:10px;padding-top:30px\">Pages:</div>");
            for (int i = 1; i <= n; i++) {
                sb.append("<a style = \"padding-right:10px;padding-up:10px\" href=\"http://localhost:8080/tickets/view?page=" + i + "\" title=\"" + i + "\">" + i + "</a>")
                ;
            }
            return sb.toString();
        }
        return "";
    }

}