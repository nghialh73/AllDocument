package com.example.alldocument.ui.document;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alldocument.R;
import com.example.alldocument.data.model.FileModel;
import com.example.alldocument.data.model.HomeItem;
import com.example.alldocument.ui.ViewModelFactory;
import com.example.alldocument.ui.home.HomeAdapter;
import com.example.alldocument.ui.home.HomeFragment;
import com.example.alldocument.ui.home.HomeViewModel;
import com.example.alldocument.utils.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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
        View view = inflater.inflate(R.layout.fragment_document, container, false);
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
