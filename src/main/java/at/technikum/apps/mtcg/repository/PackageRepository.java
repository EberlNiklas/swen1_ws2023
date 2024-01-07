package at.technikum.apps.mtcg.repository;

import at.technikum.apps.mtcg.entity.Package;

import java.util.Optional;
import java.util.Set;

public interface PackageRepository {

    Package save(Package pkg);

    Package update(Package oldPkg, Package newPkg);


}
