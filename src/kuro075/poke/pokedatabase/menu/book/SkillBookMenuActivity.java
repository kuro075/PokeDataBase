package kuro075.poke.pokedatabase.menu.book;

import kuro075.poke.pokedatabase.data_base.store.DataStore;
import kuro075.poke.pokedatabase.data_base.store.DataStore.DataTypes;

public class SkillBookMenuActivity extends BookMenuActivity{

	@Override
	protected DataTypes getDataType() {
		return DataStore.DataTypes.SKILL;
	}

}
