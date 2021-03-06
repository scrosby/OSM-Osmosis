/**
 * Copyright 2010 Scott A. Crosby
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
 *
 * @author Scott A. Crosby <scott@sacrosby.com>
 *
 */
package crosby.binary.osmosis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.openstreetmap.osmosis.core.OsmosisRuntimeException;
import org.openstreetmap.osmosis.core.pipeline.common.TaskConfiguration;
import org.openstreetmap.osmosis.core.pipeline.common.TaskManager;
import org.openstreetmap.osmosis.core.pipeline.common.TaskManagerFactory;
import org.openstreetmap.osmosis.core.pipeline.v0_6.SinkManager;

import crosby.binary.file.BlockOutputStream;

/**
 * The task manager factory for a binary (PBF) writer.
 */
public class OsmosisSerializerFactory extends TaskManagerFactory {
    private static final String ARG_FILE_NAME = "file";
    private static final String DEFAULT_FILE_NAME = "dump.osm.pbf";

    @Override
    protected TaskManager createTaskManagerImpl(TaskConfiguration taskConfig) {
        // TODO Auto-generated method stub
        String fileName;
        File file;
        OsmosisSerializer task = null;

        // Get the task arguments.
        fileName = getStringArgument(taskConfig, ARG_FILE_NAME,
                getDefaultStringArgument(taskConfig, DEFAULT_FILE_NAME));

        // Create a file object from the file name provided.
        file = new File(fileName);

        // Build the task object.
        try {
            BlockOutputStream output = new BlockOutputStream(
                    new FileOutputStream(file));
            task = new OsmosisSerializer(output);
            task.configBatchLimit(this.getIntegerArgument(taskConfig,
                    "batchlimit", 8000));
            task.configOmit(this.getBooleanArgument(taskConfig, "omitmetadata",
                    false));
            task.setUseDense(this.getBooleanArgument(taskConfig, "usedense",
                true));
            task.configGranularity(this.getIntegerArgument(taskConfig,
                    "granularity", 100));

            output.setCompress(this.getStringArgument(taskConfig, "compress",
                    "deflate"));

        } catch (FileNotFoundException e) {
        	throw new OsmosisRuntimeException("Failed to initialize Osmosis pbf serializer.", e);
        }

        return new SinkManager(taskConfig.getId(), task, taskConfig
                .getPipeArgs());
    }
}
