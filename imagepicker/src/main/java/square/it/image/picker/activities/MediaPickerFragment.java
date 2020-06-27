package square.it.image.picker.activities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.MediaColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import java.util.ArrayList;
import java.util.List;

import square.it.image.picker.MediaAdapter;
import square.it.image.picker.MediaItem;
import square.it.image.picker.MediaOptions;
import square.it.image.picker.MediaSelectedListener;
import square.it.image.picker.R;
import square.it.image.picker.utils.MediaUtils;
import square.it.image.picker.utils.Utils;
import square.it.image.picker.widget.HeaderGridView;
import square.it.image.picker.widget.PickerImageView;


/**
 * @author TUNGDX
 */

/**
 * Display list of videos, photos from {@link MediaStore} and select one or many
 * item from list depends on {@link MediaOptions} that passed when open media
 * picker.
 */
public class MediaPickerFragment extends BaseFragment implements
        LoaderManager.LoaderCallbacks<Cursor>, OnItemClickListener {
    private static final String LOADER_EXTRA_URI = "loader_extra_uri";
    private static final String LOADER_EXTRA_PROJECT = "loader_extra_project";
    private static final String LOADER_EXTRA_SELECTION = "loader_extra_selection";
    private static final String LOADER_EXTRA_SELECTION_ARGS = "loader_extra_selection_args";
    private static final String IS_BUCKET = "isBucket";
    private static final String KEY_GRID_STATE = "grid_state";
    private static final String KEY_MEDIA_SELECTED_LIST = "media_selected_list";
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 100;

    private HeaderGridView mGridView;
    private TextView mNoItemView;
    private MediaAdapter mMediaAdapter;
    private MediaOptions mMediaOptions;
    private MediaSelectedListener mMediaSelectedListener;
    private Bundle mSavedInstanceState;
    private List<MediaItem> mMediaSelectedList;

    private int mPhotoSize, mPhotoSpacing;

    private String bucketName;

    private boolean isBucket;
    Activity context;

    public MediaPickerFragment() {
        mSavedInstanceState = new Bundle();
    }

    public static MediaPickerFragment newInstance(MediaOptions options, String bucket) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(MediaPickerActivity.EXTRA_MEDIA_OPTIONS, options);
        bundle.putString(LOADER_EXTRA_SELECTION_ARGS, bucket);
        bundle.putBoolean(IS_BUCKET, true);
        MediaPickerFragment fragment = new MediaPickerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static MediaPickerFragment newInstance(MediaOptions options) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(MediaPickerActivity.EXTRA_MEDIA_OPTIONS, options);
        bundle.putBoolean(IS_BUCKET, false);
        MediaPickerFragment fragment = new MediaPickerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMediaSelectedListener = (MediaSelectedListener) activity;
        context = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mMediaOptions = savedInstanceState
                    .getParcelable(MediaPickerActivity.EXTRA_MEDIA_OPTIONS);
            mMediaSelectedList = savedInstanceState
                    .getParcelableArrayList(KEY_MEDIA_SELECTED_LIST);
            mSavedInstanceState = savedInstanceState;
        } else {

            mMediaOptions = getArguments().getParcelable(MediaPickerActivity.EXTRA_MEDIA_OPTIONS);
            mMediaSelectedList = mMediaOptions.getMediaListSelected();
            isBucket = getArguments().getBoolean(IS_BUCKET);
            if(isBucket){
                bucketName = getArguments().getString(LOADER_EXTRA_SELECTION_ARGS);
            }
        }
        // get the photo size and spacing
        mPhotoSize = getResources().getDimensionPixelSize(
                R.dimen.picker_photo_size);
        mPhotoSpacing = getResources().getDimensionPixelSize(
                R.dimen.picker_photo_spacing);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mediapicker, container,
                false);
        context.setTitle("Select Photo");
        initView(root);
        return root;
    }

    private void requestPhotos(boolean isRestart) {
        requestMedia(Images.Media.EXTERNAL_CONTENT_URI,
                MediaUtils.PROJECT_PHOTO, isRestart);
    }

    private void requestMedia(Uri uri, String[] projects, boolean isRestart) {
        Bundle bundle = new Bundle();
        bundle.putStringArray(LOADER_EXTRA_PROJECT, projects);
        bundle.putString(LOADER_EXTRA_URI, uri.toString());
        if(isBucket){
            bundle.putStringArray(LOADER_EXTRA_SELECTION_ARGS, new String[]{bucketName});
        }

        if (isRestart)
            getLoaderManager().restartLoader(0, bundle, this);
        else
            getLoaderManager().initLoader(0, bundle, this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mGridView != null) {
            mSavedInstanceState.putParcelable(KEY_GRID_STATE,
                    mGridView.onSaveInstanceState());
        }
        mSavedInstanceState.putParcelable(
                MediaPickerActivity.EXTRA_MEDIA_OPTIONS, mMediaOptions);
        mSavedInstanceState.putParcelableArrayList(KEY_MEDIA_SELECTED_LIST,
                (ArrayList<MediaItem>) mMediaSelectedList);
        outState.putAll(mSavedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        Uri uri = Uri.parse(bundle.getString(LOADER_EXTRA_URI));
        String[] projects = bundle.getStringArray(LOADER_EXTRA_PROJECT);
        if(isBucket){
            String[] selectionArgs = bundle.getStringArray(LOADER_EXTRA_SELECTION_ARGS);
            String selection = MediaUtils.SELECTION_BUCKET;
            return new CursorLoader(mContext, uri, projects, selection, selectionArgs, MediaUtils.ORDER_BY);
        }
        return new CursorLoader(mContext, uri, projects, null, null, MediaUtils.ORDER_BY);
    }

    private void bindData(Cursor cursor) {
        if (cursor == null || cursor.getCount() <= 0) {
            switchToError();
            return;
        }
        switchToData();
        if (mMediaAdapter == null) {
            mMediaAdapter = new MediaAdapter(mContext, cursor, 0,
                    mMediaImageLoader, mMediaOptions);
        } else {
            mMediaAdapter.swapCursor(cursor);
        }
        if (mGridView.getAdapter() == null) {
            mGridView.setAdapter(mMediaAdapter);
            mGridView.setRecyclerListener(mMediaAdapter);
        }
        Parcelable state = mSavedInstanceState.getParcelable(KEY_GRID_STATE);
        if (state != null) {
            mGridView.onRestoreInstanceState(state);
        }
        if (mMediaSelectedList != null) {
            mMediaAdapter.setMediaSelectedList(mMediaSelectedList);
        }
        mMediaAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        bindData(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Preference:http://developer.android.com/guide/components/loaders.html#callback
        if (mMediaAdapter != null)
            mMediaAdapter.swapCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Object object = parent.getAdapter().getItem(position);
        if (object instanceof Cursor) {
            Uri uri;
            uri = MediaUtils.getPhotoUri((Cursor) object);
            PickerImageView pickerImageView = (PickerImageView) view
                    .findViewById(R.id.thumbnail);
            MediaItem mediaItem = new MediaItem(MediaItem.PHOTO, uri);
            mMediaAdapter.updateMediaSelected(mediaItem, pickerImageView);
            mMediaSelectedList = mMediaAdapter.getMediaSelectedList();

            if (mMediaAdapter.hasSelected()) {
                mMediaSelectedListener.onHasSelected(mMediaAdapter
                        .getMediaSelectedList());
            } else {
                mMediaSelectedListener.onHasNoSelected();
            }
        }
    }

    public void switchMediaSelector() {
        requestPhotos(true);
    }

    public List<MediaItem> getMediaSelectedList() {
        return mMediaSelectedList;
    }

    public boolean hasMediaSelected() {
        return mMediaSelectedList != null && mMediaSelectedList.size() > 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mGridView != null) {
            mSavedInstanceState.putParcelable(KEY_GRID_STATE,
                    mGridView.onSaveInstanceState());
            mGridView = null;
        }
        if (mMediaAdapter != null) {
            mMediaAdapter.onDestroyView();
        }
    }

    public int getMediaType() {
        return MediaItem.PHOTO;
    }

    private void switchToData() {
        mNoItemView.setVisibility(View.GONE);
        mNoItemView.setText(null);
        mGridView.setVisibility(View.VISIBLE);
    }

    private void switchToError() {
        mNoItemView.setVisibility(View.VISIBLE);
        mNoItemView.setText(R.string.picker_no_items);
        mGridView.setVisibility(View.GONE);
    }

    private void initView(View view) {
        mGridView = (HeaderGridView) view.findViewById(R.id.grid);
        View header = new View(context);
        ViewGroup.LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                Utils.getActionbarHeight(context));
        header.setLayoutParams(params);
        mGridView.addHeaderView(header);

        mGridView.setOnItemClickListener(this);
        mNoItemView = (TextView) view.findViewById(R.id.no_data);

        // get the view tree observer of the grid and set the height and numcols
        // dynamically
        mGridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (mMediaAdapter != null
                                && mMediaAdapter.getNumColumns() == 0) {
                            final int numColumns = (int) Math.floor(mGridView
                                    .getWidth() / (mPhotoSize + mPhotoSpacing));
                            if (numColumns > 0) {
                                final int columnWidth = (mGridView.getWidth() / numColumns)
                                        - mPhotoSpacing;
                                mMediaAdapter.setNumColumns(numColumns);
                                mMediaAdapter.setItemHeight(columnWidth);
                            }
                        }
                    }
                });
    }

    private void requestMedia() {
        requestPhotos(false);
    }

    private void performRequestMedia() {
        if (hasPermission()) {
            requestMedia();
        } else {
            requestReadingExternalStoragePermission();
        }
    }

    private void requestReadingExternalStoragePermission() {
        requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"},
                REQUEST_READ_EXTERNAL_STORAGE);
    }

    private boolean hasPermission() {
        int permission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestMedia();
                }
                return;
        }
        //handle permissions that passed from the host activity.
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            requestMedia();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        performRequestMedia();
    }
}