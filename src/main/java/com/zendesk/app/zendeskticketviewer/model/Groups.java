package com.zendesk.app.zendeskticketviewer.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Groups {

    private Boolean defaultField;
    private Boolean deleted;
    private String updated_at;
    private String name;
    private String description;
    private String created_at;
    private Long id;
    private String url;

    public static String convertToHTML(List<Groups> stuffs){
        StringBuilder sb = new StringBuilder();
        sb.append("<ul>\n");
        for(Groups cd : stuffs){
            sb.append("<li>Ticket with subject \""+ cd.getName() + "\" opened by \"" + cd.getId() + "\" \n");
            sb.append("\n");
        }
        return sb.toString();
    }

}
