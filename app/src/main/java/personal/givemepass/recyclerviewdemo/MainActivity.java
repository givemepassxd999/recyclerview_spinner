package personal.givemepass.recyclerviewdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	private Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = getApplicationContext();
		ArrayList<MyData> myDataset = new ArrayList<>();
		for(int i = 0; i < 100; i++){
			MyData data = new MyData();
			data.setName(i + "");
			List<String> l = new ArrayList<>();
			l.add("a");
			l.add("b");
			l.add("c");
			data.setmDataList(l);
			data.setmSelectIndex(0);
			myDataset.add(data);
		}
		MyAdapter myAdapter = new MyAdapter(myDataset);
		RecyclerView mList = (RecyclerView) findViewById(R.id.list_view);
		final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mList.setLayoutManager(layoutManager);
		mList.setAdapter(myAdapter);
	}

	private class MyData{
		private String name;
		private int mSelectIndex;
		private List<String> mDataList;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getmSelectIndex() {
			return mSelectIndex;
		}

		public void setmSelectIndex(int mSelectIndex) {
			this.mSelectIndex = mSelectIndex;
		}

		public List<String> getmDataList() {
			return mDataList;
		}

		public void setmDataList(List<String> mDataList) {
			this.mDataList = mDataList;
		}
	}

	private class SpinnerAdapter extends BaseAdapter{
		private List<String> datas;

		public SpinnerAdapter() {
			datas = new ArrayList<>();
		}

		public void setData(List<String> list){
			datas = list;
		}

		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			Holder holder;
			if(view == null){
				view = LayoutInflater.from(mContext).inflate(R.layout.spinner_item, null);
				holder = new Holder();
				holder.mTextView = (TextView) view.findViewById(R.id.spinner_text);
				view.setTag(holder);
			} else{
				holder = (Holder) view.getTag();
			}
			holder.mTextView.setText(datas.get(position));
			return view;
		}
		class Holder{
			private TextView mTextView;
		}
	}

	public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
		private List<MyData> mData;

		public class ViewHolder extends RecyclerView.ViewHolder {
			public TextView mTextView;
			public android.widget.Spinner mSpinner;
			public SpinnerAdapter mSpinnerAdapter;
			public ViewHolder(View v) {
				super(v);
				mTextView = (TextView) v.findViewById(R.id.info_text);
				mSpinner = (Spinner) v.findViewById(R.id.spinner);
				mSpinnerAdapter = new SpinnerAdapter();
				mSpinner.setAdapter(mSpinnerAdapter);

			}
		}

		public MyAdapter(List<MyData> data) {
			mData = data;
		}

		@Override
		public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View v = LayoutInflater.from(parent.getContext())
					.inflate(R.layout.item, parent, false);
			ViewHolder vh = new ViewHolder(v);
			return vh;
		}

		@Override
		public void onBindViewHolder(final ViewHolder holder, final int position) {
			holder.mTextView.setText(mData.get(position).getName());
			int index = mData.get(position).getmSelectIndex();
			holder.mSpinnerAdapter.setData(mData.get(position).getmDataList());
			holder.mSpinnerAdapter.notifyDataSetChanged();
			holder.mSpinner.setSelection(index);
			holder.mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int p, long id) {
					mData.get(position).setmSelectIndex(p);
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {

				}
			});
		}

		@Override
		public int getItemCount() {
			return mData.size();
		}
	}
}
