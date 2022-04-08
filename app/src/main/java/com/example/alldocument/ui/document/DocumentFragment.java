package com.example.alldocument.ui.document;

import static com.example.alldocument.ui.document.DocumentActivity.HOME_TYPE_ITEM_KEY;
import static com.example.alldocument.ui.document.DocumentActivity.NAME_ITEM_KEY;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alldocument.R;
import com.example.alldocument.data.model.FileModel;
import com.example.alldocument.data.model.HomeItemType;
import com.example.alldocument.ui.MainActivity;
import com.example.alldocument.ui.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DocumentFragment extends Fragment {

    @BindView(R.id.rcv_document)
    RecyclerView rcv_document;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
//    @BindView(R.id.edt_search)
//    AppCompatEditText edt_search;

    private DocumentViewModel viewModel;
    private GridLayoutManager gridLayoutManager;
    private DocumentAdapter documentAdapter;
    private List<FileModel> fileModels = new ArrayList<>();

    public static DocumentFragment newInstance(HomeItemType type) {
        DocumentFragment fragment = new DocumentFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(HOME_TYPE_ITEM_KEY, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_document, container, false);
        ButterKnife.bind(this, view);
        ViewModelFactory factory = new ViewModelFactory(requireActivity().getApplication());
        viewModel = new ViewModelProvider(requireActivity(), factory).get(DocumentViewModel.class);
        showLoading();
        initData();
        gridLayoutManager = new GridLayoutManager(requireActivity(), 3);
        documentAdapter = new DocumentAdapter(requireActivity(), fileModels);
        rcv_document.setAdapter(documentAdapter);
        return view;
    }

    private void initData() {
        HomeItemType type = (HomeItemType) getArguments().getSerializable(HOME_TYPE_ITEM_KEY);
        viewModel.setData(type);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            requireActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        viewModel.getData().observe(getViewLifecycleOwner(), fileModels -> {
            if (fileModels != null) {
                hideLoading();
                documentAdapter.addAllData(fileModels);
            }
        });
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        rcv_document.setVisibility(View.GONE);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
        rcv_document.setVisibility(View.VISIBLE);
    }
}
