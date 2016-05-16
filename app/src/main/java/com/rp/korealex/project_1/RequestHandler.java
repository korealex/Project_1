package com.rp.korealex.project_1;

import java.util.ArrayList;

/**
 * Created by korealex on 3/13/16.
 */
public class RequestHandler {


    public int current_page;
    public int previous_page;
    public int next_page;
    public int total_results;
    public int total_pages;
    public ArrayList<ResponseData> responseData = new ArrayList<>();


    public RequestHandler() {

    }

    public RequestHandler(int current_page, int total_pages, int total_results) {
        this.total_pages = total_pages;
        this.current_page = current_page;
        this.previous_page = current_page - 1;
        this.next_page = current_page + 1;
        this.total_results = total_results;
    }

    public void addResponseData(ResponseData movie) {
        responseData.add(movie);

    }


    public ArrayList<ResponseData> getResponseData() {

        return responseData;
    }


}
