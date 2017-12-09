package tool_xinhua;

import android.app.Activity;
import android.widget.Toast;

public class Tool_Toast extends Activity {
	String tiptext;	
	public void toastTip(String tiptext)
	{
		Toast.makeText(getApplicationContext(), tiptext, Toast.LENGTH_SHORT).show();
	}
}
