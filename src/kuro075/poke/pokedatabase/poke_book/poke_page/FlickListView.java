package kuro075.poke.pokedatabase.poke_book.poke_page;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * フリック対応、リストビュー
 * @author sanogenma
 *
 */
public class FlickListView extends ListView{
	OnTouchListener listener;
	public FlickListView(Context context,OnTouchListener listener) {
		super(context);
		// TODO Auto-generated constructor stub
		this.listener=listener;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
    	listener.onTouch(this, event);
		return super.onTouchEvent(event);
	}
}
