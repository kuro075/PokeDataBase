package kuro075.poke.pokedatabase;

import kuro075.poke.pokedatabase.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * 複数選択時のポケモンリストの項目
 * @author sanogenma
 *
 */
public class MultipleChoicePokeListItem extends LinearLayout implements Checkable{
	private CheckedTextView checked_text_view;
	
	
	public MultipleChoicePokeListItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setLayoutParams( new ListView.LayoutParams( ListView.LayoutParams.FILL_PARENT ,
                ListView.LayoutParams.WRAP_CONTENT ) );

        View view =
                ( ( LayoutInflater ) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE ) ).inflate(
                        R.layout.poke_multiple_choice_row_layout , this , false );

        checked_text_view=(CheckedTextView)view.findViewById(R.id.checked_text_view);
        addView(view);
	}

	@Override
	public boolean isChecked() {
		// TODO Auto-generated method stub
		return checked_text_view.isChecked();
	}

	@Override
	public void setChecked(boolean checked) {
		// TODO Auto-generated method stub
		checked_text_view.setChecked(checked);
	}

	@Override
	public void toggle() {
		// TODO Auto-generated method stub
		setChecked(isChecked());
	}

}
