package fr.xgouchet.texteditor;

import java.io.File;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import fr.xgouchet.androidlib.R;
import fr.xgouchet.androidlib.ui.Toaster;
import fr.xgouchet.androidlib.ui.activity.BrowsingActivity;
import fr.xgouchet.texteditor.common.Constants;

public class TedOpenActivity extends BrowsingActivity implements OnClickListener, Constants {

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int request;
		Bundle extras;

		// Setup content view
		setContentView(R.layout.layout_open);

		// buttons
		findViewById(R.id.buttonCancel).setOnClickListener(this);

		// set default result
		setResult(RESULT_CANCELED, null);

		// show the title as toast
		extras = getIntent().getExtras();
		if (extras != null)
			request = extras.getInt(EXTRA_REQUEST_CODE);
		else
			request = -1;

		switch (request) {
		case REQUEST_OPEN:
			Toaster.showToast(this, R.string.toast_open_select, false);
			break;
		case REQUEST_HOME_PAGE:
			Toaster.showToast(this, R.string.toast_home_page_select, false);
			break;
		}

	}

	/**
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View v) {
		if (v.getId() == R.id.buttonCancel) {
			setResult(RESULT_CANCELED);
			finish();
		}
	}

	/**
	 * @see fr.xgouchet.androidlib.ui.activity.BrowserActivity#onFolderClick(java.io.File)
	 */
	protected boolean onFolderClick(File folder) {
		return true;
	}

	/**
	 * @see fr.xgouchet.androidlib.ui.activity.BrowsingActivity#onFolderViewFilled()
	 */
	protected void onFolderViewFilled() {

	}

	/**
	 * @see fr.xgouchet.androidlib.ui.activity.BrowserActivity#onFileClick(java.io.File)
	 */
	protected void onFileClick(File file) {
		if (setOpenResult(file))
			finish();
	}

	/**
	 * Set the result of this activity to open a file
	 * 
	 * @param file
	 *            the file to return
	 * @return if the result was set correctly
	 */
	protected boolean setOpenResult(File file) {
		Intent result;

		if (!file.canRead()) {
			Toaster.showToast(this, R.string.toast_file_cant_read, true);
			return false;
		}

		result = new Intent();
		result.putExtra("path", file.getAbsolutePath());

		setResult(RESULT_OK, result);
		return true;
	}
}
