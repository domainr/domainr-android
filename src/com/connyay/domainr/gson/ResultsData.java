package com.connyay.domainr.gson;

public class ResultsData {
    private String domain;
    private String host;
    private String subdomain;
    private String path;
    private String availability;
    private String register_url;

    public String getDomain() {
	return domain;
    }

    public void setDomain(String domain) {
	this.domain = domain;
    }

    public String getHost() {
	return host;
    }

    public void setHost(String host) {
	this.host = host;
    }

    public String getSubdomain() {
	return subdomain;
    }

    public void setSubdomain(String subdomain) {
	this.subdomain = subdomain;
    }

    public String getPath() {
	return path;
    }

    public void setPath(String path) {
	this.path = path;
    }

    public String getAvailability() {
	return availability;
    }

    public void setAvailability(String availability) {
	this.availability = availability;
    }

    public String getRegister_url() {
	return register_url;
    }

    public void setRegister_url(String register_url) {
	this.register_url = register_url;
    }
}
