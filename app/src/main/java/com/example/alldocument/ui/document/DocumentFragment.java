package com.example.alldocument.ui.document;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.alldocument.R;
import com.example.alldocument.data.model.FileModel;
import com.example.alldocument.ui.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class DocumentFragment extends Fragment {

    private DocumentViewModel viewModel;
    private GridLayoutManager gridLayoutManager;
    private DocumentAdapter documentAdapter;
    private List<FileModel> fileModels = new ArrayList<>();

    public static DocumentFragment newInstance() {
        return new DocumentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        ViewModelFactory factory = new ViewModelFactory(requireActivity().getApplication());
        viewModel = new ViewModelProvider(requireActivity(), factory).get(DocumentViewModel.class);

        //initData();
        gridLayoutManager = new GridLayoutManager(requireActivity(), 3);
        documentAdapter = new DocumentAdapter(requireActivity(), fileModels);
        //rcv_all_pdf.setAdapter(homeAdapter);
        //rcv_all_pdf.setLayoutManager(gridLayoutManager);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        if (!PermissionUtils.checkPermission(getActivity())) {
//            PermissionUtils.requestPermission(requireActivity());
//        } else {
//            loadData();
//        }
    }
}
