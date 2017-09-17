/*
 *     Copyright [2017] [Haibo(Tristan) Yan]
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.haibo.mobile.android.flicks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by Haibo(Tristan) Yan on 9/16/17.
 */

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.ivMovie) ImageView ivMovie;

    @BindView(R.id.ivPlay) ImageView ivPlay;

    @BindView(R.id.tvTitle) TextView tvTitle;

    @BindView(R.id.tvReleaseDate) TextView tvReleaseDate;

    @BindView(R.id.ivRate1) ImageView ivRate1;
    @BindView(R.id.ivRate2) ImageView ivRate2;
    @BindView(R.id.ivRate3) ImageView ivRate3;
    @BindView(R.id.ivRate4) ImageView ivRate4;
    @BindView(R.id.ivRate5) ImageView ivRate5;

    @BindView(R.id.tvOverview) TextView tvOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_detail_movie);
        ButterKnife.bind(this);

        final String id = getIntent().getStringExtra("id");
        String imagePath = getIntent().getStringExtra("image_path");
        final boolean isBackdrop = getIntent().getBooleanExtra("backdrop", false);

        Picasso.with(this).load(imagePath)
                .placeholder(R.drawable.default_movie)
                .transform(new RoundedCornersTransformation(10, 10))
                .into(ivMovie);
        if (!isBackdrop) {
            ivPlay.setVisibility(View.GONE);
        }

        ivMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, YouTubePlayerActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("backdrop", isBackdrop);
                startActivity(intent);
            }
        });

        String title = getIntent().getStringExtra("title");
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(title);

        String releaseDate = getIntent().getStringExtra("release_date");
        tvReleaseDate.setText(String.format("Release Date: %s", releaseDate));

        double rate = getIntent().getDoubleExtra("rate", 5.0);
        ivRate1.setImageResource(rate >= 1.0 ? R.drawable.full_star : R.drawable.hollow_start);
        ivRate2.setImageResource(rate >= 2.0 ? R.drawable.full_star : R.drawable.hollow_start);
        ivRate3.setImageResource(rate >= 3.0 ? R.drawable.full_star : R.drawable.hollow_start);
        ivRate4.setImageResource(rate >= 4.0 ? R.drawable.full_star : R.drawable.hollow_start);
        ivRate5.setImageResource(rate >= 5.0 ? R.drawable.full_star : R.drawable.hollow_start);

        String overview = getIntent().getStringExtra("overview");
        tvOverview.setText(overview);
    }

    public void onSubmit(View v) {
        // closes the activity and returns to first screen
        this.finish();
    }
}
