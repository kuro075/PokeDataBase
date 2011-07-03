package kuro075.poke.pokedatabase.type_book;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager.TypeData;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager.TypeRelations;
import kuro075.poke.pokedatabase.type_book.type_page.TypePageActivity;
import kuro075.poke.pokedatabase.util.Utility;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TypeRelationTableActivity extends TypeBookActivity{
	private static String TAG="TypeRelationTableActivity";
	
	/**
	 * このアクティビティーをstartさせる
	 * @param context
	 */
	public static void startThisActivity(Context context){
		Utility.log(TAG, "startThisActivity");
		Intent intent = new Intent(context,TypeRelationTableActivity.class);
		context.startActivity(intent);
	}
	
	//cellの幅
	private final int cell_width=37;
	private final int DEFENSE_HEIGHT=70;
	//文字の大きさ
	private final int ATTACK_SIZE=15;
	private final int DEFENSE_SIZE=cell_width/2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.type_relation_table_layout);
		
		//攻撃タイプを初期化
		initAttackType();
		
		//防御側タイプを初期化
		initDefenseType();
	}

	/**
	 * 左側の攻撃タイプを初期化
	 */
	private void initAttackType(){
		TableLayout layout=(TableLayout)findViewById(R.id.layout_attack_type);
		final Context context=this;
		for(final TypeData type:TypeData.values()){
			TableRow tr=new TableRow(layout.getContext());
			tr.setPadding(0,0,0,1);
			TextView tv=new TextView(tr.getContext());
			tv.setText(type.toString());
			tv.setGravity(Gravity.CENTER);
			tv.setTextColor(type.getColor());
			tv.setTextSize(ATTACK_SIZE);
			tv.setBackgroundColor(Color.BLACK);
			tv.setMinimumHeight(cell_width);
			tv.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					TypePageActivity.startThisActivity(context, type);
				}
			});
			tr.addView(tv);
			layout.addView(tr);
			//cell_width=tr.getHeight();
		}
	}
	
	/**
	 * 上側の防御タイプを初期化
	 */
	private void initDefenseType(){
		TableLayout layout=(TableLayout)findViewById(R.id.layout_defense_type);
		layout.setStretchAllColumns(true);
		TableRow tr=new TableRow(layout.getContext());
		final Context context=this;
		for(final TypeData type:TypeData.values()){
			LinearLayout ll=new LinearLayout(tr.getContext());
			ll.setOrientation(LinearLayout.VERTICAL);
			ll.setBackgroundColor(Color.WHITE);
			ll.setPadding(1,0,0,1);
			ll.setMinimumWidth(cell_width);
			
			TextView tv=new TextView(ll.getContext());
			tv.setText(type.getShortName());
			tv.setGravity(Gravity.CENTER);
			tv.setTextColor(type.getColor());
			tv.setBackgroundColor(Color.BLACK);
			tv.setTextSize(DEFENSE_SIZE);
			tv.setMinimumHeight(DEFENSE_HEIGHT);
			tv.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					TypePageActivity.startThisActivity(context, type);
				}
			});
			ll.addView(tv);
			tr.addView(ll);
		}
		layout.addView(tr);
		
		/*
		 * 相性表を埋める
		 */
		for(TypeData attack_type:TypeData.values()){
			tr=new TableRow(layout.getContext());
			for(TypeData defense_type:TypeData.values()){
				LinearLayout ll=new LinearLayout(tr.getContext());
				ll.setBackgroundColor(Color.WHITE);
				ll.setPadding(1,0,0,1);
				ll.setMinimumWidth(cell_width);
				
				TextView tv=new TextView(ll.getContext());
				TypeRelations relation=attack_type.attackTo(defense_type);
				tv.setText(relation.getFigure());
				tv.setTextSize(DEFENSE_SIZE);
				tv.setMinimumHeight(cell_width);
				tv.setMinimumWidth(cell_width);
				tv.setBackgroundColor(Color.BLACK);
				tv.setGravity(Gravity.CENTER);
				
				ll.addView(tv);
				tr.addView(ll);
			}
			layout.addView(tr);
		}
	}
}
