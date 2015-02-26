/*
* Copyright (C) 2015 Fabien Devos.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.nanotasks.nanotasksample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.nanotasks.BackgroundWork;
import com.nanotasks.Completion;
import com.nanotasks.Tasks;

import java.util.concurrent.TimeUnit;

public class MainActivity extends ActionBarActivity {

  @InjectView(R.id.button) Button button;
  @InjectView(R.id.progress) ProgressBar progress;
  @OnClick(R.id.button) void onClickButton() {
    doLongTask();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);
  }

  private void showProgress() {
    progress.setVisibility(View.VISIBLE);
    button.setVisibility(View.INVISIBLE);
  }

  private void hideProgress() {
    progress.setVisibility(View.INVISIBLE);
    button.setVisibility(View.VISIBLE);
  }

  private void doLongTask() {
    showProgress();
    Tasks.executeInBackground(this, backgroundWork, completion);
  }

  private BackgroundWork<String> backgroundWork = new BackgroundWork<String>() {
    @Override public String doInBackground() throws Exception {
      final int DELAY = 3;
      Thread.sleep(TimeUnit.SECONDS.toMillis(DELAY));
      return "Worked hard for " + DELAY + " seconds";
    }
  };

  private Completion<String> completion = new Completion<String>() {
    @Override public void onSuccess(Context context, String result) {
      Toast.makeText(context, result, Toast.LENGTH_LONG).show();
      hideProgress();
    }

    @Override public void onError(Context context, Exception e) {
      Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
      hideProgress();
    }
  };

}
