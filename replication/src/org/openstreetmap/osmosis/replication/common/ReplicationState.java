// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.replication.common;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.openstreetmap.osmosis.core.store.StoreClassRegister;
import org.openstreetmap.osmosis.core.store.StoreReader;
import org.openstreetmap.osmosis.core.store.StoreWriter;
import org.openstreetmap.osmosis.core.store.Storeable;
import org.openstreetmap.osmosis.core.time.DateFormatter;
import org.openstreetmap.osmosis.core.time.DateParser;


/**
 * Contains the state to be remembered between replication invocations. This state ensures that no
 * data is missed during replication, and none is repeated.
 */
public class ReplicationState implements Storeable {
	private Date timestamp;
	private long sequenceNumber;


	/**
	 * Creates a new instance.
	 * 
	 * @param txnMax
	 *            The maximum transaction id in the database.
	 * @param txnMaxQueried
	 *            The maximum transaction id currently replicated from the database.
	 * @param txnActive
	 *            The currently active transaction ids.
	 * @param txnReady
	 *            The previously active transaction ids that can now be queried.
	 * @param timestamp
	 *            The maximum timestamp of data currently read from the database.
	 * @param sequenceNumber
	 *            The replication sequence number.
	 */
	public ReplicationState(long txnMax, long txnMaxQueried, List<Long> txnActive, List<Long> txnReady,
			Date timestamp, long sequenceNumber) {
		this.timestamp = timestamp;
		this.sequenceNumber = sequenceNumber;
	}
	
	
	/**
	 * Creates a new instance.
	 * 
	 * @param reader
	 *            The store to read state from.
	 * @param scr
	 *            Maintains the mapping between classes and their identifiers
	 *            within the store.
	 */
	public ReplicationState(StoreReader reader, StoreClassRegister scr) {
		timestamp = new Date(reader.readLong());
		sequenceNumber = reader.readLong();
	}
	
	
	/**
	 * Creates a new instance.
	 * 
	 * @param properties
	 *            The properties to load state from.
	 */
	public ReplicationState(Properties properties) {
		timestamp = new DateParser().parse(properties.getProperty("timestamp"));
		sequenceNumber = Long.parseLong(properties.getProperty("sequenceNumber"));
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void store(StoreWriter writer, StoreClassRegister storeClassRegister) {
		writer.writeLong(timestamp.getTime());
		writer.writeLong(sequenceNumber);
	}


	/**
	 * Writes all state into the provided properties object.
	 * 
	 * @param properties
	 *            The properties to be updated.
	 */
	public void store(Properties properties) {
		properties.setProperty("timestamp", new DateFormatter().format(timestamp));
		properties.setProperty("sequenceNumber", Long.toString(sequenceNumber));
	}


	/**
	 * Gets the maximum timestamp of data currently read from the database.
	 * 
	 * @return The timestamp.
	 */
	public Date getTimestamp() {
		return timestamp;
	}


	/**
	 * Sets the maximum timestamp of data currently read from the database.
	 * 
	 * @param timestamp
	 *            The timestamp.
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
	/**
	 * Gets the replication sequence number.
	 * 
	 * @return The sequence number.
	 */
	public long getSequenceNumber() {
		return sequenceNumber;
	}


	/**
	 * Sets the replication sequence number.
	 * 
	 * @param sequenceNumber
	 *            The sequence number.
	 */
	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result;
		
		if (obj instanceof ReplicationState) {
			ReplicationState compareState = (ReplicationState) obj;
			
			if (timestamp.equals(compareState.timestamp)
					&& sequenceNumber == compareState.sequenceNumber) {
				result = true;
			} else {
				result = false;
			}
		} else {
			result = false;
		}
		
		return result;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return (int) sequenceNumber + (int) timestamp.getTime();
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "ReplicationState(timestamp=" + timestamp + ", sequenceNumber=" + sequenceNumber + ")";
	}
}
