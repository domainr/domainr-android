package com.domainr.common;

import java.util.HashMap;
import java.util.Map;

import com.flurry.android.FlurryAgent;

public class FlurryLogger {

	public static void logSearch(String query) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("Query", query);
		FlurryAgent.logEvent("Search", params);

	}

	public static void logSearchTap(String domain) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("Result tap", domain);
		FlurryAgent.logEvent("Search Result tap", params);

	}

	public static void logDomainrShare() {

		FlurryAgent.logEvent("Shared via Email");

	}

	public static void logDomainShare(String domain) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("SearchTap", domain);
		FlurryAgent.logEvent("Shared via Email", params);

	}

	public static void logUncaught(String ex) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("Throwable", ex);
		FlurryAgent.logEvent("Uncaught", params);

	}

	public static void logDomainRegister(String register) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("Register URL", register);
		FlurryAgent.logEvent("Register tap", params);

	}
}
