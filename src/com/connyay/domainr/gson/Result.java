package com.connyay.domainr.gson;

public class Result {
    private String query;
    private String domain;
    private String domain_idna;
    private String host;
    private String subdomain;
    private String path;
    private String availability;
    private Tld tld;
    // private String subdomains; -- Don't think this is currently used
    private String subregistration_permitted;
    private String www_url;
    private String whois_url;
    private String register_url;
    private Registrars[] registrars;

    public String getQuery() {
	return query;
    }

    public void setQuery(String query) {
	this.query = query;
    }

    public String getDomain() {
	return domain;
    }

    public void setDomain(String domain) {
	this.domain = domain;
    }

    public String getDomain_idna() {
	return domain_idna;
    }

    public void setDomain_idna(String domain_idna) {
	this.domain_idna = domain_idna;
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

    public Tld getTld() {
	return tld;
    }

    public void setTld(Tld tld) {
	this.tld = tld;
    }

    public String getSubregistration_permitted() {
	return subregistration_permitted;
    }

    public void setSubregistration_permitted(String subregistration_permitted) {
	this.subregistration_permitted = subregistration_permitted;
    }

    public String getWww_url() {
	return www_url;
    }

    public void setWww_url(String www_url) {
	this.www_url = www_url;
    }

    public String getWhois_url() {
	return whois_url;
    }

    public void setWhois_url(String whois_url) {
	this.whois_url = whois_url;
    }

    public String getRegister_url() {
	return register_url;
    }

    public void setRegister_url(String register_url) {
	this.register_url = register_url;
    }

    public Registrars[] getRegistrars() {
	return registrars;
    }

    public void setRegistrars(Registrars[] registrars) {
	this.registrars = registrars;
    }

}
