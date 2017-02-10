package edu.psu.swe.scim.server.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import edu.psu.swe.scim.spec.extension.EnterpriseExtension;
import edu.psu.swe.scim.spec.extension.EnterpriseExtension.Manager;
import edu.psu.swe.scim.spec.protocol.data.PatchOperation;
import edu.psu.swe.scim.spec.protocol.data.PatchOperation.Type;
import edu.psu.swe.scim.spec.resources.Address;
import edu.psu.swe.scim.spec.resources.Email;
import edu.psu.swe.scim.spec.resources.Name;
import edu.psu.swe.scim.spec.resources.PhoneNumber;
import edu.psu.swe.scim.spec.resources.ScimUser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UpdateRequestTest {

  @Test
  public void testResourcePassthrough() {
    UpdateRequest<ScimUser> updateRequest = new UpdateRequest<>();
    updateRequest.initWithResource("1234", createUser1(), createUser2());
    ScimUser result = updateRequest.getResource();
    log.info("testResourcePassthrough: " + result);
    Assertions.assertThat(result)
              .isNotNull();
  }

  @Test
  public void testPatchPassthrough() {
    UpdateRequest<ScimUser> updateRequest = new UpdateRequest<>();
    updateRequest.initWithPatch("1234", createUser1(), createUser1PatchOps());
    List<PatchOperation> result = updateRequest.getPatchOperations();
    log.info("testPatchPassthrough: " + result);
    Assertions.assertThat(result)
              .isNotNull();
  }

  @Test
  public void testPatchToUpdate() {
    UpdateRequest<ScimUser> updateRequest = new UpdateRequest<>();
    updateRequest.initWithPatch("1234", createUser1(), createUser1PatchOps());
    ScimUser result = updateRequest.getResource();
    log.info("testPatchToUpdate: " + result);
    Assertions.assertThat(result)
              .isNotNull();
  }

  @Test
  public void testResourceToPatch() {
    UpdateRequest<ScimUser> updateRequest = new UpdateRequest<>();
    updateRequest.initWithResource("1234", createUser1(), createUser2());
    List<PatchOperation> result = updateRequest.getPatchOperations();
    log.info("testResourceToPatch: " + result);
    Assertions.assertThat(result)
              .isNotNull();
  }

  public static ScimUser createUser1() {
    ScimUser user = new ScimUser();
    user.setId("912345678");
    user.setExternalId("912345678");
    user.setActive(true);
    user.setDisplayName("John Anyman");
    user.setNickName(user.getDisplayName());
    user.setTitle("Professor");
    user.setUserName("jxa123");

    Name name = new Name();
    name.setGivenName("John");
    name.setMiddleName("Xander");
    name.setFamilyName("Anyman");
    name.setHonorificSuffix("Jr.");
    user.setName(name);

    Address homeAddress = new Address();
    homeAddress.setType("home");
    homeAddress.setStreetAddress("123 Fake Street");
    homeAddress.setLocality("State College");
    homeAddress.setRegion("Pennsylvania");
    homeAddress.setCountry("USA");
    homeAddress.setPostalCode("16801");

    Address workAddress = new Address();
    workAddress.setType("work");
    workAddress.setStreetAddress("2 Old Main");
    workAddress.setLocality("State College");
    workAddress.setRegion("Pennsylvania");
    workAddress.setCountry("USA");
    workAddress.setPostalCode("16802");

    List<Address> address = Stream.of(workAddress, homeAddress)
                                  .collect(Collectors.toList());
    user.setAddresses(address);

    Email workEmail = new Email();
    workEmail.setPrimary(true);
    workEmail.setType("work");
    workEmail.setValue("jxa123@psu.edu");
    workEmail.setDisplay("jxa123@psu.edu");

    Email homeEmail = new Email();
    homeEmail.setPrimary(true);
    homeEmail.setType("home");
    homeEmail.setValue("john@gmail.com");
    homeEmail.setDisplay("jxa123@psu.edu");

    Email otherEmail = new Email();
    otherEmail.setPrimary(true);
    otherEmail.setType("other");
    otherEmail.setValue("outside@version.net");
    otherEmail.setDisplay("outside@version.net");

    List<Email> emails = Stream.of(homeEmail, workEmail)
                               .collect(Collectors.toList());
    user.setEmails(emails);

    PhoneNumber homePhone = new PhoneNumber();
    homePhone.setValue("+1 (814)867-5309");
    homePhone.setType("home");
    homePhone.setPrimary(true);

    PhoneNumber workPhone = new PhoneNumber();
    workPhone.setValue("+1 (814)867-5309");
    workPhone.setType("home");
    workPhone.setPrimary(true);

    List<PhoneNumber> phones = Stream.of(homePhone, workPhone)
                                     .collect(Collectors.toList());
    user.setPhoneNumbers(phones);

    EnterpriseExtension enterpriseExtension = new EnterpriseExtension();
    enterpriseExtension.setEmployeeNumber("7865");
    enterpriseExtension.setDepartment("Dept B.");
    Manager manager = new Manager();
    manager.setValue("Pointy Haired Boss");
    manager.setRef("45353");
    enterpriseExtension.setManager(manager);
    user.addExtension(enterpriseExtension);

    return user;
  }

  public static ScimUser createUser2() {
    ScimUser user = new ScimUser();
    user.setId("912345678");
    user.setExternalId("912345678");
    user.setActive(false);
    user.setDisplayName("John Anyman");
    user.setNickName(user.getDisplayName());
    user.setTitle("Professor");
    user.setUserName("jxa123@psu.edu");

    Name name = new Name();
    name.setGivenName("John");
    name.setMiddleName("Xander");
    name.setFamilyName("Anyman");
    name.setHonorificSuffix("Jr.");
    user.setName(name);

    Address homeAddress = new Address();
    homeAddress.setType("home");
    homeAddress.setStreetAddress("123 Fake Street");
    homeAddress.setLocality("State College");
    homeAddress.setRegion("Pennsylvania");
    homeAddress.setCountry("USA");
    homeAddress.setPostalCode("16801");

    Address workAddress = new Address();
    workAddress.setType("work");
    workAddress.setStreetAddress("200 Science Park Road");
    workAddress.setLocality("State College");
    workAddress.setRegion("Pennsylvania");
    workAddress.setCountry("USA");
    workAddress.setPostalCode("16803");

    List<Address> address = Stream.of(homeAddress, workAddress)
                                  .collect(Collectors.toList());
    // List<Address> address =
    // Stream.of(workAddress,homeAddress).collect(Collectors.toList());
    user.setAddresses(address);

    Email workEmail = new Email();
    workEmail.setPrimary(true);
    workEmail.setType("work");
    workEmail.setValue("jxa123@psu.edu");
    workEmail.setDisplay(null);

    Email homeEmail = new Email();
    homeEmail.setPrimary(true);
    homeEmail.setType("home");
    homeEmail.setValue("john@hotmail.com");
    homeEmail.setDisplay("jxa123@psu.edu");

    List<Email> emails = Stream.of(homeEmail, workEmail)
                               .collect(Collectors.toList());
    user.setEmails(emails);

    PhoneNumber homePhone = new PhoneNumber();
    homePhone.setValue("+1 (814)867-5309");
    homePhone.setType("home");
    homePhone.setPrimary(true);

    PhoneNumber workPhone = new PhoneNumber();
    workPhone.setValue("+1 (814)867-5309");
    workPhone.setType("home");
    workPhone.setPrimary(true);

    List<PhoneNumber> phones = Stream.of(homePhone, workPhone)
                                     .collect(Collectors.toList());
    user.setPhoneNumbers(phones);

    EnterpriseExtension enterpriseExtension = new EnterpriseExtension();
    enterpriseExtension.setEmployeeNumber("1234");
    enterpriseExtension.setDepartment("Dept A.");
    Manager manager = new Manager();
    manager.setValue("Pointy Haired Boss");
    manager.setRef("45353");
    enterpriseExtension.setManager(manager);
    user.addExtension(enterpriseExtension);

    return user;
  }

  private List<PatchOperation> createUser1PatchOps() {
    List<PatchOperation> patchOperations = new ArrayList<>();
    PatchOperation removePhoneNumberOp = new PatchOperation();
    removePhoneNumberOp.setOpreration(Type.REMOVE);
    removePhoneNumberOp.setPath("phoneNumbers[type eq \"home\"]");
    patchOperations.add(removePhoneNumberOp);
    return patchOperations;
  }

}