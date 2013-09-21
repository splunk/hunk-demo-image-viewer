package com.splunk.hunk.output;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.MapFile.Writer;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.io.Text;

public class StoresImages {

	public final Class<Text> keyClass = Text.class;
	public final Class<BytesWritable> valueClass = BytesWritable.class;
	private Path inputDir;
	private Path outputSeqFile;
	private FileSystem inFs;
	private FileSystem outFs;

	public StoresImages(FileSystem inFs, Path inputDir, FileSystem outFs,
			Path outputSeqFile) {
		this.inFs = inFs;
		this.inputDir = inputDir;
		this.outFs = outFs;
		this.outputSeqFile = outputSeqFile;
	}

	public Path storeImages() throws IOException {
		Writer writer = null;
		try {
			writer = createWriter();
			writer.setIndexInterval(2);
			writeFiles(writer, inFs.listStatus(inputDir));
		} finally {
			IOUtils.closeQuietly(writer);
		}
		return outputSeqFile;
	}

	private Writer createWriter() throws IOException {
		return new MapFile.Writer(outFs.getConf(), outFs, outputSeqFile.toUri()
				.getPath(), keyClass, valueClass, CompressionType.NONE);
	}

	private void writeFiles(Writer writer, FileStatus[] listStatus)
			throws IOException {
		for (FileStatus f : listStatus)
			if (!f.isDir())
				appendFileContent(writer, f);
			else
				writeFiles(writer, inFs.listStatus(f.getPath()));
	}

	private void appendFileContent(Writer writer, FileStatus f)
			throws IOException {
		Path p = f.getPath();
		ByteArrayOutputStream fileBytes = readFilesBytes(p);
		writer.append(new Text(p.toUri().getPath()), new BytesWritable(
				fileBytes.toByteArray()));
	}

	private ByteArrayOutputStream readFilesBytes(Path p) throws IOException {
		ByteArrayOutputStream fileBytes = null;
		FSDataInputStream open = null;
		try {
			fileBytes = new ByteArrayOutputStream();
			open = inFs.open(p);
			IOUtils.copyLarge(open, fileBytes);
		} finally {
			IOUtils.closeQuietly(open);
			IOUtils.closeQuietly(fileBytes);
		}
		return fileBytes;
	}

	public static StoresImages create(String dirFsUri, String dir,
			String outFsUri, String output) throws IOException {
		return new StoresImages(FileSystem.get(URI.create(dirFsUri),
				new Configuration()), new Path(dir), FileSystem.get(
				URI.create(outFsUri), new Configuration()), new Path(output));
	}
}
