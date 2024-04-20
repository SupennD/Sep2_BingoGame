package mediator;

import utility.observer.subject.RemoteSubject;

/**
 * An interface intended to be implemented by classes that want to expose themselves as remote objects. This should be
 * the same on client and server. If you make updates to one, make sure to update the other one as well.
 *
 * @author Alexandru Tofan
 * @version 1.0.0 - April 2024
 */
public interface RemoteModel extends RemoteSubject<Object, Object>
{
  // TODO: implement
}