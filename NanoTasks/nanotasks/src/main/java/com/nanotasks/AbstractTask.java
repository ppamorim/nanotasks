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

import android.os.AsyncTask;
import android.util.Log;

/**
 * Abstract class that's generate a AsyncTask and allow some types of callback.
 *
 * @author Fabien Devos.
 */
protected abstract class AbstractTask<T> extends AsyncTask<Void, Void, T> {

  private BackgroundWork<T> backgroundWork;
  private Exception exception;

  public AbstractTask(BackgroundWork<T> backgroundWork) {
    this.backgroundWork = backgroundWork;
  }

  @Override protected final T doInBackground(Void... params) {
    try {
      return backgroundWork.doInBackground();
    } catch (Exception e) {
      exception = e;
      return null;
    }
  }

  @Override protected final void onPostExecute(T result) {
    if (!isCancelled()) {
      if (exception == null) {
        onSuccess(result);
      } else {
        Log.w(this.getClass().getSimpleName(), exception);
        onError(exception);
      }
    }
  }

  protected abstract void onSuccess(T result);
  protected abstract void onError(Exception e);

}
