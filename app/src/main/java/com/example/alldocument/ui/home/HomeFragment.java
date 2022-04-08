package com.example.alldocument.ui.home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alldocument.R;
import com.example.alldocument.data.model.HomeItem;
import com.example.alldocument.data.model.HomeItemType;
import com.example.alldocument.ui.ViewModelFactory;
import com.example.alldocument.ui.document.DocumentActivity;
import com.example.alldocument.ui.document.DocumentFragment;
import com.example.alldocument.utils.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements HomeAdapter.HomeAdapterOnItemClickListener {
    @BindView(R.id.rcv_files)
    RecyclerView rcv_all_pdf;
    @BindView(R.id.edt_search)
    AppCompatEditText edt_search;

    private HomeViewModel viewModel;
    private GridLayoutManager gridLayoutManager;
    private HomeAdapter homeAdapter;
    private List<HomeItem> homeItems = new ArrayList<>();

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        ViewModelFactory factory = new ViewModelFactory(requireActivity().getApplication());
        viewModel = new ViewModelProvider(requireActivity(), factory).get(HomeViewModel.class);
        initData();
        gridLayoutManager = new GridLayoutManager(requireActivity(), 3);
        homeAdapter = new HomeAdapter(requireActivity(), homeItems, this);
        rcv_all_pdf.setAdapter(homeAdapter);
        rcv_all_pdf.setLayoutManager(gridLayoutManager);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!PermissionUtils.checkPermission(getActivity())) {
            PermissionUtils.requestPermission(requireActivity());
        } else {
            loadData();
        }
    }

    private void loadData() {
        viewModel.getAllData().observe(getViewLifecycleOwner(), fileModels -> {
            if (fileModels != null) {
                homeAdapter.updateItemCount(fileModels.size(), 0);
            }
        });
        viewModel.getPDFData().observe(getViewLifecycleOwner(), fileModels -> {
            if (fileModels != null) {
                homeAdapter.updateItemCount(fileModels.size(), 1);
            }
        });
        viewModel.getWordData().observe(getViewLifecycleOwner(), fileModels -> {
            if (fileModels != null) {
                homeAdapter.updateItemCount(fileModels.size(), 2);
            }
        });
        viewModel.getPowerPointData().observe(getViewLifecycleOwner(), fileModels -> {
            if (fileModels != null) {
                homeAdapter.updateItemCount(fileModels.size(), 3);
            }
        });
        viewModel.getExcelData().observe(getViewLifecycleOwner(), fileModels -> {
            if (fileModels != null) {
                homeAdapter.updateItemCount(fileModels.size(), 4);
            }
        });
        viewModel.getTextData().observe(getViewLifecycleOwner(), fileModels -> {
            if (fileModels != null) {
                homeAdapter.updateItemCount(fileModels.size(), 5);
            }
        });
        viewModel.getRecentData().observe(getViewLifecycleOwner(), fileModels -> {
            if (fileModels != null) {
                homeAdapter.updateItemCount(fileModels.size(), 6);
            }
        });
        viewModel.getFavoriteData().observe(getViewLifecycleOwner(), fileModels -> {
            if (fileModels != null) {
                homeAdapter.updateItemCount(fileModels.size(), 7);
            }
        });
        viewModel.getImageData().observe(getViewLifecycleOwner(), fileModels -> {
            if (fileModels != null) {
                homeAdapter.updateItemCount(fileModels.size(), 8);
            }
        });
    }

    private void initData() {
        homeItems.add(new HomeItem(
                requireActivity().getString(R.string.all_file),
                HomeItemType.ALL,
                R.drawable.ic_all,
                R.drawable.background_color_all, 0
        ));
        homeItems.add(new HomeItem(
                requireActivity().getString(R.string.pdf_file),
                HomeItemType.PDF,
                R.drawable.ic_pdf,
                R.drawable.background_color_pdf, 0
        ));
        homeItems.add(new HomeItem(
                requireActivity().getString(R.string.word_file),
                HomeItemType.WORD,
                R.drawable.ic_doc,
                R.drawable.background_color_doc, 0
        ));
        homeItems.add(new HomeItem(
                requireActivity().getString(R.string.power_point),
                HomeItemType.POWER_POINT,
                R.drawable.ic_ppt,
                R.drawable.background_color_ppt, 0
        ));
        homeItems.add(new HomeItem(
                requireActivity().getString(R.string.excel_file),
                HomeItemType.EXCEL,
                R.drawable.ic_xls,
                R.drawable.background_color_excel, 0
        ));
        homeItems.add(new HomeItem(
                requireActivity().getString(R.string.text_file),
                HomeItemType.TEXT,
                R.drawable.ic_txt,
                R.drawable.background_color_txt, 0
        ));
        homeItems.add(new HomeItem(
                requireActivity().getString(R.string.recent_file),
                HomeItemType.RECENT,
                R.drawable.ic_recent,
                R.drawable.background_color_recent, 0
        ));
        homeItems.add(new HomeItem(
                requireActivity().getString(R.string.favorite_file),
                HomeItemType.FAVORITE,
                R.drawable.ic_favourite,
                R.drawable.background_color_favorite, 0
        ));
        homeItems.add(new HomeItem(
                requireActivity().getString(R.string.screen_shot),
                HomeItemType.SCREEN,
                R.drawable.ic_jpg,
                R.drawable.background_color_screen, 0
        ));
    }

    @Override
    public void onItemClick(HomeItem homeItem, int position) {
        Intent intent = new Intent(requireActivity(), DocumentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(DocumentActivity.HOME_TYPE_ITEM_KEY, homeItem.getType());
        bundle.putString(DocumentActivity.NAME_ITEM_KEY, homeItem.getName());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /*protected void openFileDetail(FileModel fileModel) {
        Intent intent = new Intent();
        fileModel.setTimeRecent(System.currentTimeMillis());

        if (FileUtils.PDF.equals(FileUtils.getFileType(fileModel.getPath()))) {
            intent.setClass(this, PdfViewerActivity.class);
        } else {
            intent.setClass(this, AppActivity.class);
        }

        intent.putExtra(MainConstant.INTENT_FILED_FILE_PATH, fileModel);
        startActivity(intent);
    }

    protected void openFileDetailFormUri(Uri uri) {
        Intent intent = new Intent();
        if (FileUtils.getFileType(uri.getPath()).equals(FileUtils.PDF)) {
            intent.setClass(this, PdfViewerActivity.class);
            intent.setClassName(this,
                    "com.ezmobi.officereaderlib.screen.details.pdf.PdfViewerActivity");
            intent.putExtra(MainConstant.INTENT_FILED_FILE_URI, uri);
            startActivity(intent);
        } else {
            try {
                File file = PathUtils.from(this, uri);
                FileModel fileModel = new FileModel();
                fileModel.setPath(file.getPath());
                fileModel.setName(PathUtils.getFileName(this, uri));
                intent.setClass(this, AppActivity.class);
                intent.putExtra(MainConstant.INTENT_FILED_FILE_PATH, fileModel);
                startActivity(intent);
                Log.e("File", file.getPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/
}
