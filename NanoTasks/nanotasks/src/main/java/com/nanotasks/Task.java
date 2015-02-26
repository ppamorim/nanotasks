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
package com.nanotasks;

import android.app.Activity;
import android.content.Context;
import java.lang.ref.WeakReference;

/**
 * A interface that's have a generic type and
 * allow callback to tasks.
 *
 * @author Fabien Devos.
 */
public class Task<T> extends AbstractTask<T> {

  private WeakReference<Context> weakContext;
  private Completion<T> completion;

  public Task(Context context, BackgroundWork<T> backgroundWork, Completion<T> completion) {
    super(backgroundWork);
    this.weakContext = new WeakReference<>(context);
    this.completion = completion;
  }

  @Override protected void onSuccess(T result) {
    if(weakContext != null) {
      final Context context = weakContext.get();
      if (completion != null
          && context != null
          && context instanceof Activity
          && activityIsStarted(new WeakReference<>((Activity)context))) {
        completion.onSuccess(context, result);
      }
    }
  }

  @Override protected void onError(Exception e) {
    if(weakContext != null) {
      final Context context = weakContext.get();
      if (completion != null && context != null) {
        completion.onError(context, e);
      }
    }
  }

  private boolean activityIsStarted(WeakReference<Activity> activity) {
    return !activity.get().isFinishing();
  }

}
