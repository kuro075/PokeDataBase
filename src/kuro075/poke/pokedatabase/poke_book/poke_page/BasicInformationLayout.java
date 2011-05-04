package kuro075.poke.pokedatabase.poke_book.poke_page;

import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import android.content.Context;
import android.widget.LinearLayout;

public class BasicInformationLayout extends LinearLayout{
	private PokeData poke;
	
	public BasicInformationLayout(Context context,PokeData poke) {
		super(context);
		// TODO Auto-generated constructor stub
		this.poke=poke;
	}

}
