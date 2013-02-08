package com.connyay.domainr.gson;

public class Results {
    private String query;
    private ResultsData[] results;

    public Results() {
    }

    public Results(String stuff) {
	setQuery(null);
	setResults(null);
    }

    public String getQuery() {
	return query;
    }

    public void setQuery(String query) {
	this.query = query;
    }

    public ResultsData[] getResults() {
	return results;
    }

    public void setResults(ResultsData[] results) {
	this.results = results;
    }

}
