package kuro075.poke.pokedatabase.data_base.character;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.character_book.character_page.CharacterPageActivity;
import kuro075.poke.pokedatabase.data_base.BasicData;
import kuro075.poke.pokedatabase.data_base.search.poke.PokeSearchableInformations;
import kuro075.poke.pokedatabase.data_base.viewable_informations.CharacterViewableInformations;
import kuro075.poke.pokedatabase.poke_book.PokeSearchResultActivity;

/**
 * 特性のデータクラス
 * @author sanogenma
 *
 */
public class CharacterData extends BasicData{


	protected static class Builder{
		private String name="-";//名前
		private int no=9999;//管理ナンバー
		private String battle_effect="-";//戦闘中の効果
		private String field_effect="-";//フィールド上の効果
		
		protected Builder(){}

		public CharacterData build(){
			return new CharacterData(name,no,battle_effect,field_effect);
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setNo(int no) {
			this.no = no;
		}

		public void setBattle_effect(String battle_effect) {
			this.battle_effect = battle_effect;
		}

		public void setField_effect(String field_effect) {
			this.field_effect = field_effect;
		}
		
		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String battle_effect;//戦闘中の効果
	private final String field_effect;//フィールド上の効果
	
	
	private CharacterData(String name,int no,String battle_effect,String field_effect) {
		super(name,no);
		// TODO Auto-generated constructor stub
		this.battle_effect=battle_effect;
		this.field_effect=field_effect;
	}

	@Override
	public int compareTo(BasicData another) {
		// TODO Auto-generated method stub
		return getNo()-another.getNo();
	}

	/**
	 * 戦闘中の効果を取得
	 * @return　戦闘中の効果
	 */
	public String getBattleEffect() {
		return battle_effect;
	}

	/**
	 * フィールド上の効果を取得
	 * @return　フィールド上の効果
	 */
	public String getFieldEffect() {
		return field_effect;
	}

	/**
	 * 特性詳細表示ダイアログを開く
	 * @param charaname
	 */
	public void openDialog(final Context context){
		AlertDialog.Builder builder;
		AlertDialog alertDialog;
		
		LayoutInflater factory=LayoutInflater.from(context);
		final View layout = factory.inflate(R.layout.dialog_charadata,null);
		//戦闘中の効果
		final TextView battleeffect=(TextView)layout.findViewById(R.id.text_battleeffect);
		battleeffect.setText(CharacterViewableInformations.BATTLE_EFFECT.getInformation(this));
		//フィールド上の効果
		final TextView fieldeffect=(TextView)layout.findViewById(R.id.text_fieldeffect);
		fieldeffect.setText(CharacterViewableInformations.FIELD_EFFECT.getInformation(this));
		
		
		builder = new AlertDialog.Builder(context);
		builder.setTitle(toString());
		builder.setView(layout);
		final CharacterData chara=this;

		builder.setPositiveButton("表示", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				CharacterPageActivity.startThisActivity(context, chara);
				dialog.dismiss();
			}
		});
		builder.setNeutralButton("検索", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				PokeSearchResultActivity.startThisActivityWithDefaultSearch(context, PokeSearchableInformations.CHARACTER, getName());
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("閉じる", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		alertDialog = builder.create();
		alertDialog.show();
	}
	
}
