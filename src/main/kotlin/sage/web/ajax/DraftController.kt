package sage.web.ajax

import org.springframework.web.bind.annotation.*
import sage.domain.commons.DomainException
import sage.entity.Draft
import sage.entity.User
import sage.web.auth.Auth

@RestController
@RequestMapping("/drafts")
open class DraftController {
  @RequestMapping("/save", method = arrayOf(RequestMethod.POST))
  open fun save(@RequestParam(required = false) draftId: Long?,
                @RequestParam(required = false) targetId: Long?,
                @RequestParam(defaultValue = "") title: String,
                @RequestParam(defaultValue = "") content: String): Long {
    val uid = Auth.checkUid()
    return draftId?.let { Draft.byId(it) }?.let {
      if (it.owner.id != uid) {
        throw DomainException("User[$uid] is not the owner of Draft[$draftId]")
      }
      it.title = title
      it.content = content
      it.update()
      it.id
    } ?: let {
      val draft = Draft(targetId ?: 0, title, content, owner = User.ref(uid))
      draft.save()
      draft.id
    }
  }
}