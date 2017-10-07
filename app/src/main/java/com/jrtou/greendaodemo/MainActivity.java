package com.jrtou.greendaodemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jrtou.greendaodemo.adapter.MyRecyclerAdapter;
import com.jrtou.greendaodemo.sqlite.BookEntry;
import com.jrtou.greendaodemo.sqlite.BookEntryDao;
import com.jrtou.greendaodemo.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;

    private Button btnInsert, btnDelete, btnEdit, btnSubmit, btnCancel;
    private TextView tvStatus;
    private EditText etName, etPrice, etMemo;

    private List<BookEntry> mBookEntryList = new ArrayList<>();
    private int mDBMode = 0;//0:等待 1:新增 2:修改
    private int position = -1;

    private BookEntryDao mBookDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setUIEnable(false);
        DBUtils dbUtils = DBUtils.Instance(this);
        mBookDao = dbUtils.getBookDao();

        mBookEntryList = mBookDao.loadAll();
        adapter.setList(mBookEntryList);
    }

    private void findViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerAdapter(mBookEntryList);
        adapter.setRecyclerViewItemClick(new MyRecyclerAdapter.RecyclerViewItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                MainActivity.this.position = position;
                tvStatus.setText("" + position);
                etName.setText(mBookEntryList.get(position).getBookName());
                etPrice.setText("$" + mBookEntryList.get(position).getBookPrice());
                etMemo.setText(mBookEntryList.get(position).getMemo());
            }
        });
        mRecyclerView.setAdapter(adapter);

        btnInsert = (Button) findViewById(R.id.insert);
        btnInsert.setEnabled(true);
        btnInsert.setOnClickListener(this);

        btnDelete = (Button) findViewById(R.id.delete);
        btnDelete.setEnabled(true);
        btnDelete.setOnClickListener(this);

        btnEdit = (Button) findViewById(R.id.edit);
        btnEdit.setEnabled(true);
        btnEdit.setOnClickListener(this);

        btnSubmit = (Button) findViewById(R.id.submit);
        btnSubmit.setOnClickListener(this);

        btnCancel = (Button) findViewById(R.id.cancel);
        btnCancel.setOnClickListener(this);

        etName = (EditText) findViewById(R.id.etBookName);
        etPrice = (EditText) findViewById(R.id.etBookPrice);
        etMemo = (EditText) findViewById(R.id.etMemo);

        tvStatus = (TextView) findViewById(R.id.tvStatus);
    }


    private void setControl(int id) {
        switch (id) {
            case R.id.insert:
                mDBMode = 1;
                tvStatus.setText("新增");
                etName.setText("");
                etMemo.setText("");
                etPrice.setText("");
                break;
            case R.id.edit:
                mDBMode = 2;
                tvStatus.setText("修改");
                break;
        }
    }

    private void setUIEnable(boolean isConfirm) {
        adapter.setList(mBookEntryList);
        tvStatus.setText("");
        mDBMode = 0;
        position = -1;

        btnSubmit.setEnabled(isConfirm);
        btnCancel.setEnabled(isConfirm);

        btnInsert.setEnabled(!isConfirm);
        btnDelete.setEnabled(!isConfirm);
        btnEdit.setEnabled(!isConfirm);

        etName.setEnabled(isConfirm);
        etPrice.setEnabled(isConfirm);
        etMemo.setEnabled(isConfirm);
    }

    private void doInsertOrEdit() {
        BookEntry entry = new BookEntry();
        entry.setBookName(etName.getText().toString());
        entry.setBookPrice(etPrice.getText().toString());
        entry.setMemo(etMemo.getText().toString());
        mBookDao.insertOrReplace(entry);

        if (mDBMode == 1) {
            mBookEntryList.add(entry);
        } else if (mDBMode == 2) {
            mBookEntryList.set(position, entry);
        }

        adapter.setList(mBookEntryList);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete:
                if (position >= 0 && null != mBookEntryList.get(position) && !etName.getText().toString().isEmpty()) {
                    mBookDao.delete(mBookEntryList.get(position));
                    mBookEntryList.remove(position);
                    position = -1;
                } else {
                    Toast.makeText(this, "無資料可刪除", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.insert:
            case R.id.edit:
                setUIEnable(true);
                setControl(view.getId());
                break;
            case R.id.submit:
            case R.id.cancel:
                if (!etName.getText().toString().isEmpty() && !etPrice.getText().toString().isEmpty() &&
                        view.getId() == R.id.submit) {
                    doInsertOrEdit();
                }
                setUIEnable(false);
                break;
        }


    }
}
