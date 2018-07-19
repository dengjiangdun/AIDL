/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.caching.internal.controller;

import com.google.common.io.CountingInputStream;
import org.gradle.caching.BuildCacheEntryReader;

import java.io.IOException;
import java.io.InputStream;

public class CommandBackedEntryReader<T> implements BuildCacheEntryReader {

    private final BuildCacheLoadCommand<T> command;

    private long bytes;
    private BuildCacheLoadCommand.Result<T> result;

    public CommandBackedEntryReader(BuildCacheLoadCommand<T> command) {
        this.command = command;
    }

    @Override
    public void readFrom(InputStream input) throws IOException {
        if (getResult() != null) {
            throw new IllegalStateException("Build cache entry has already been read");
        }
        CountingInputStream countingInputStream = new CountingInputStream(input);
        result = command.load(countingInputStream);
        bytes = countingInputStream.getCount();
    }

    public long getBytes() {
        return bytes;
    }

    public BuildCacheLoadCommand.Result<T> getResult() {
        return result;
    }
}
