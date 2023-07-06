package org.blbulyandavbulyan.simplestore.services.exceptions;

public abstract class ResourceNotFoundException extends StoreException{
    protected Object searchCriteria;
    protected ResourceNotFoundException(String message, Object searchCriteria) {
        super(message);
        this.searchCriteria = searchCriteria;
    }

    public Object getSearchCriteria() {
        return searchCriteria;
    }
}
