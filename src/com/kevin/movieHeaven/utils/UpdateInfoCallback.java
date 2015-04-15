package com.kevin.movieHeaven.utils;

import com.kevin.movieHeaven.models.UpdateInfo;

public interface UpdateInfoCallback {
    public void succeed(UpdateInfo info, boolean hasNextPage);

    public void failed();
}
