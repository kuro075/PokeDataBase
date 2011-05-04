package kuro075.poke.pokedatabase.poke_book.poke_page;

import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import android.content.Context;
import android.widget.FrameLayout;

public class EggSkillInformationLayout extends FrameLayout{

	private PokeData poke;
	public EggSkillInformationLayout(Context context,PokeData poke) {
		super(context);
		// TODO Auto-generated constructor stub
		this.poke=poke;
	}

}
