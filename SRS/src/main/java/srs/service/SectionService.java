package srs.service;

import srs.dao.DaoFactory;
import srs.dao.SectionDao;

public class SectionService {
	private SectionDao sectionDao = (SectionDao)DaoFactory.createReleventDao("SectionDao");
}
