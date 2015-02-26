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

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import android.os.Build;
import java.util.concurrent.Executor;

/**
 * A interface that's have a generic type and
 * allow callback to tasks.
 *
 * @author Fabien Devos.
 */
public final class Tasks {

  private Tasks() { throw new UnsupportedOperationException(); }

  @SuppressLint("NewApi") public static <T> void executeInBackground(Context context, BackgroundWork<T> backgroundWork, Completion<T> completion) {
    executeInBackground(context, backgroundWork, completion, AsyncTask.THREAD_POOL_EXECUTOR);
  }

  public static <T> void executeInBackground(Context context, BackgroundWork<T> backgroundWork, Completion<T> completion, Executor executor) {
    Task<T> task = new Task<>(context, backgroundWork, completion);
    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB) {
      task.executeOnExecutor(executor);
    } else {
      task.execute();
    }
  }

}
