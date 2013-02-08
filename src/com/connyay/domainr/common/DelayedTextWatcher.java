package com.connyay.domainr.common;

import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;

public abstract class DelayedTextWatcher implements TextWatcher {

    private long delayTime;
    private WaitTask lastWaitTask;

    public DelayedTextWatcher(long delayTime) {
	super();
	this.delayTime = delayTime;
    }

    @Override
    public void afterTextChanged(Editable s) {
	synchronized (this) {
	    if (lastWaitTask != null) {
		lastWaitTask.cancel(true);
	    }
	    lastWaitTask = new WaitTask();
	    lastWaitTask.execute(s);
	}
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
	    int after) {
	// TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
	// TODO Auto-generated method stub

    }

    public abstract void afterTextChangedDelayed(Editable s);

    private class WaitTask extends AsyncTask<Editable, Void, Editable> {

	@Override
	protected Editable doInBackground(Editable... params) {
	    try {
		Thread.sleep(delayTime);
	    } catch (InterruptedException e) {
	    }
	    return params[0];
	}

	@Override
	protected void onPostExecute(Editable result) {
	    super.onPostExecute(result);
	    afterTextChangedDelayed(result);
	}
    }

}
