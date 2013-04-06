package com.domainr;

import java.lang.Thread.UncaughtExceptionHandler;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.widget.LinearLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.domainr.R;
import com.domainr.common.FlurryLogger;

public class MoreInfoFragment extends Fragment implements OnClickListener,
	UncaughtExceptionHandler {
    LinearLayout wiki, iana, viewSite, whois, share;

    public static Fragment newInstance() {
	MoreInfoFragment myFragment = new MoreInfoFragment();
	return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	View v = inflater.inflate(R.layout.more_info, container, false);
	wiki = (LinearLayout) v.findViewById(R.id.wiki);
	wiki.setOnClickListener(this);
	iana = (LinearLayout) v.findViewById(R.id.iana);
	iana.setOnClickListener(this);
	whois = (LinearLayout) v.findViewById(R.id.whois);
	whois.setOnClickListener(this);
	viewSite = (LinearLayout) v.findViewById(R.id.visitSite);
	viewSite.setOnClickListener(this);
	share = (LinearLayout) v.findViewById(R.id.share);
	share.setOnClickListener(this);
	return v;
    }

    @Override
    public void onClick(View v) {
	String uri = "";
	if (v == wiki) {
	    uri = getArguments().getString("wiki");
	} else if (v == iana) {
	    uri = getArguments().getString("iana");
	} else if (v == viewSite) {
	    uri = getArguments().getString("www");
	} else if (v == whois) {
	    uri = getArguments().getString("whois");
	}
	if (uri != null && v != share) {
	    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
		    Uri.parse(uri));
	    startActivity(browserIntent);

	}
	if (v == share) {
	    String domain = getArguments().getString("domain");
	    String shareBody = "Found on Domainr: \n http://domai.nr/" + domain;
	    FlurryLogger.logDomainShare(shareBody);
	    Intent sharingIntent = new Intent(
		    android.content.Intent.ACTION_SEND);
	    sharingIntent.setType("text/plain");
	    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
		    "Domainr: " + domain);
	    sharingIntent
		    .putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
	    startActivity(Intent.createChooser(sharingIntent,
		    "Share this domain"));
	}

    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
	FlurryLogger.logUncaught(ex.getMessage());

    }
}
