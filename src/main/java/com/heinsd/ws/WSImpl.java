package com.heinsd.ws;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;

import org.apache.cxf.interceptor.security.AuthenticationException;
import org.apache.log4j.Logger;

import com.heinsd.dao.DAO;
import com.heinsd.dao.DAOImpl;
import com.heinsd.dto.CreateEntryRequest;
import com.heinsd.dto.CreateEntryResponse;
import com.heinsd.dto.DeleteEntryRequest;
import com.heinsd.dto.DeleteEntryResponse;
import com.heinsd.dto.GetEntriesRequest;
import com.heinsd.dto.GetEntriesResponse;
import com.heinsd.model.DeleteParameters;
import com.heinsd.model.Entry;
import com.heinsd.model.EntrySearchParams;

public class WSImpl implements WS {
	
	/*
	 * Implementation of web service (WS) to handle incoming requests.
	 */
	@Resource
	WebServiceContext context;

	private final static Logger log = Logger.getLogger(WSImpl.class);

	@Override
	public CreateEntryResponse createPoint(CreateEntryRequest request) {
		if (request == null) {
			log.error("CreatePoint - Empty Request");
			return new CreateEntryResponse("CreatePoint - Empty Request");
		}

		DAO entryDAO = DAOImpl.getInstance();

		Entry entry = request.getData();
		// TODO validate entry
		if (entry == null) {
			log.error("CreatePoint - Empty Entry");
			return new CreateEntryResponse("CreatePoint - Empty Entry");
		}
		int id = entryDAO.insertEntry(entry);
		CreateEntryResponse response = new CreateEntryResponse();
		
		if (id > 0) {
			response.setResponse(true);
			return response;
		} else {
			log.error("CreatePoint - Entry not inserted in DB - " + entry.getId() + ":" + entry.getUsername());
			return new CreateEntryResponse("CreatePoint - Entry not created");
		}

	}

	@Override
	public GetEntriesResponse getEntryList(GetEntriesRequest request) {
		if (request == null) {
			log.error("GetEntryList - Empty Request");
			return new GetEntriesResponse("GetEntryList - Empty Request");
		}

		DAO entryDAO = DAOImpl.getInstance();

		List<Entry> entries = null;
		EntrySearchParams data = request.getData();
		if (data == null || data.getField() == null) {
			// TODO validate data
			log.error("GetEntryList - Invalid or Empty data");
			return new GetEntriesResponse("GetEntryList - Invalid or Empty data");
		}
		try {
			entries = entryDAO.getEntries(data.getUsername(), data.getType(), data.getPoint(), data.getDist(),
					data.getField());
		} catch (SQLException e) {
			log.error("GetEntryList - Unable to get entries - " + e.getSQLState() + " - " + e.getErrorCode());
			log.error("Error MSG: " + e.getMessage());
			return new GetEntriesResponse("GetEntryList - Unable to get entries");
		}
		GetEntriesResponse response = new GetEntriesResponse();
		response.setEntriesList(entries);

		return response;
	}

	@Override
	public DeleteEntryResponse deletePoint(DeleteEntryRequest request) throws AuthenticationException {
		if (request == null) {
			log.error("DeletePoint - Empty Request");
			return new DeleteEntryResponse("DeletePoint - Empty Request");
		}

		DAO entryDAO = DAOImpl.getInstance();
		DeleteParameters data = request.getData();
		if (data == null) {
			// TODO validate data
			log.error("DeletePoint - Invalid or empty data");
			return new DeleteEntryResponse("DeletePoint - Invalid or empty data");
		}
		int id = data.getId();
		String username = data.getUsername();
		if (id < 0 || username == null || username.isEmpty()) {
			log.error("DeletePoint - Invalid or empty data - " + id + " - " + username);
			return new DeleteEntryResponse("DeletePoint - Invalid or empty data");
		}
		boolean deleteEntry = entryDAO.deleteEntry(id, username);
		if (deleteEntry) {
			DeleteEntryResponse response = new DeleteEntryResponse();
			return response;
		} else {
			log.error("DeletePoint - Failed to deletePoint - " + id + " - " + username);
			return new DeleteEntryResponse("DeletePoint - Failed to deletePoint");
		}

	}

}
