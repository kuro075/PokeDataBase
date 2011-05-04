package kuro075.poke.pokedatabase.poke_book.poke_page;

import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import android.content.Context;
import android.widget.FrameLayout;

public class LvSkillInformationLayout extends FrameLayout{
	private PokeData poke;
	public LvSkillInformationLayout(Context context,PokeData poke) {
		super(context);
		// TODO Auto-generated constructor stub
		this.poke=poke;
	}

}
