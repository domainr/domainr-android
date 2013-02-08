package com.domainr;

import java.lang.Thread.UncaughtExceptionHandler;
import java.net.URLEncoder;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.Dialog;
import org.holoeverywhere.app.DialogFragment;
import org.holoeverywhere.widget.LinearLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.connyay.domainr.R;
import com.domainr.common.FlurryLogger;

public class AboutDialog extends DialogFragment implements OnClickListener,
	UncaughtExceptionHandler {
    LinearLayout mainTwitter, blog, github, contactDmnr, connyay, case_twitter,
	    rr, ceedub;

    public static DialogFragment newInstance() {
	DialogFragment dialogFragment = new AboutDialog();
	return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	builder.setView(getContentView());
	Dialog dialog = builder.create();
	return dialog;
    }

    private View getContentView() {
	LayoutInflater inflater = (LayoutInflater) getActivity()
		.getLayoutInflater();
	View v = inflater.inflate(R.layout.about, null);
	mainTwitter = (LinearLayout) v.findViewById(R.id.mainTwitter);
	mainTwitter.setOnClickListener(this);
	blog = (LinearLayout) v.findViewById(R.id.blog);
	blog.setOnClickListener(this);
	github = (LinearLayout) v.findViewById(R.id.github);
	github.setOnClickListener(this);
	contactDmnr = (LinearLayout) v.findViewById(R.id.contactDmnr);
	contactDmnr.setOnClickListener(this);
	connyay = (LinearLayout) v.findViewById(R.id.connyay);
	connyay.setOnClickListener(this);
	case_twitter = (LinearLayout) v.findViewById(R.id.case_twitter);
	case_twitter.setOnClickListener(this);
	rr = (LinearLayout) v.findViewById(R.id.rr);
	rr.setOnClickListener(this);
	ceedub = (LinearLayout) v.findViewById(R.id.ceedub);
	ceedub.setOnClickListener(this);
	return v;

    }

    @Override
    public void onClick(View v) {
	String uri = "";
	if (v == mainTwitter) {
	    uri = "http://www.twitter.com/domainr";
	} else if (v == blog) {
	    uri = "http://blog.domai.nr";
	} else if (v == connyay) {
	    uri = "http://www.twitter.com/_connyay";
	} else if (v == case_twitter) {
	    uri = "http://www.twitter.com/case";
	} else if (v == rr) {
	    uri = "http://www.twitter.com/rr";
	} else if (v == ceedub) {
	    uri = "http://www.twitter.com/ceedub";
	} else if (v == github) {
	    uri = "https://github.com/nbio/domainr-android/blob/master/LICENSE";
	}

	if (v != contactDmnr) {
	    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
		    Uri.parse(uri));
	    startActivity(browserIntent);
	} else {

	    String uriText = "mailto:ping+android@domai.nr" + "?subject="
		    + URLEncoder.encode("Hey Domainr Guys!");

	    Uri emailuri = Uri.parse(uriText);

	    Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
	    sendIntent.setData(emailuri);
	    startActivity(Intent.createChooser(sendIntent, "Send email"));

	}

    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
	FlurryLogger.logUncaught(ex.getMessage());

    }
}
