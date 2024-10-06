Overview
The Auxalis project is a 3D visualization of Earth's solar neighborhood which provides a 3D visualisation of known exoplanet systems and stars in our universe , all of which are observable enough by the HWO for characterization, rendered in the browser using WebGL (via the three.js library). The project includes all known stars and known exoplanets data from the NASA EXOPLANET ARCHIVE.

USAGE INSTRUCTIONS FOR SPACE APPS JUDGING :

**- IF need to build from source :**
1. *mvn clean package*
2. *cd target* 
3. *java -jar auxalis-1.0-SNAPSHOT-jobjar.jar*

**HOSTED AT : localhost, port 42315 http://localhost:42315/**

**USING STRAIGHT FROM THE RELEASE :**

1. Either download and extract the zip provided in the Releases section OR clone the repo and go to target folder.
2. *java -jar auxalis-1.0-SNAPSHOT-jobjar.jar*

**HOSTED AT : localhost, port 42315 http://localhost:42315/**

 
Data Sources

All exoplanet data comes from the NASA Exoplanet catalog.
Data about our own solar system and its planets are from Wikipedia.
All star data comes from the HYG database hosted on github.

Rendering
Star rendering borrows heavily from the Seed of Andromeda procedural star rendering guide, trimmed down substantially so it can (attempt) to render in real-time. The glsl shader is found here.

Exoplanet rendering is extremely dumb: if the exoplanet is > 10% the mass of Jupiter, it uses a fake vaguely gassy shader. Otherwise it uses a fake vaguely rocky shader.

Scale / Visibility
Since stars are, in the grand scheme of things, very tiny, additional rendering is needed to make them visible at huge distances. So the actual visible stars are not the stars themselves, but large transparent meshes with a similar color to the parent star.

Planets are even smaller and harder to see, so they are marked by an a spoke from the sun to the planet's location at perhelion.

Controls
Clicking on a star or planet changes the focus to that object (and zooms to it)
Mousing over a star or planet brings up a tooltip
Scrolling in / out zooms forward and back
Orbit mode always maintains a slow orbit around the object in focus, and clicking + dragging rotates the view around that object. In free look mode there is no fixed target.
Highlights
Only a fraction of stars have known exoplanets, so the “Exoplanets” highlight marker adds highlight markers on any stars with known exoplanets.

Render Radius
Show only stars within 10 / 25 / 50 / 75 light years of our own sun.

Project
The JavaScript code in this project is truly appalling-- it is truly a horror show when you get to debugging. I'm not a JavaScript developer, this is a side project, please don't judge me for it (but any suggestions or cleanup is super welcome).

The Java is also pretty gross, but I have no excuses for that.

I tried pretty hard to keep performance reasonable even on limited hardware, but it will still probably set your laptop on fire.

MATH 

Spherical coordinates:
x = r * sin(φ) * cos(θ)
y = r * sin(φ) * sin(θ)
z = r * cos(φ)
Purpose: To convert between Cartesian and spherical coordinates, which is necessary for calculating the positions of stars and planets in 3D space.
Explanation: Spherical coordinates are used to represent the position of an object in 3D space, with the radius (r) being the distance from the origin, and the azimuthal angle (θ) and polar angle (φ) being the angles from the x and z axes, respectively.

Trigonometry:
θ = acos((a · b) / (|a| |b|))
φ = acos(z / r)
DEGREE_IN_RADIANS = Math.PI / 180
Purpose: To calculate angles and distances between objects in 3D space, which is necessary for rendering the stars and planets in the correct positions.

Explanation: Trigonometry is used to calculate the angles and distances between objects in 3D space, which is necessary for rendering the stars and planets in the correct positions. The acos function is used to calculate the angle between two vectors, and the DEGREE_IN_RADIANS constant is used to convert between degrees and radians.

Vector math:
a · b = |a| |b| cos(θ)
|a| = √(a · a)
a × b = (a_y * b_z - a_z * b_y, a_z * b_x - a_x * b_z, a_x * b_y - a_y * b_x)
Purpose: To perform calculations involving vectors, such as calculating the dot product and cross product of two vectors.
Explanation: Vector math is used to perform calculations involving vectors, such as calculating the dot product and cross product of two vectors. The dot product is used to calculate the angle between two vectors, and the cross product is used to calculate the normal vector to a plane.

Quaternion math:
q1 * q2 = (w1 * w2 - x1 * x2 - y1 * y2 - z1 * z2, w1 * x2 + x1 * w2 + y1 * z2 - z1 * y2, w1 * y2 - x1 * z2 + y1 * w2 + z1 * x2, w1 * z2 + x1 * y2 - y1 * x2 + z1 * w2)
Purpose: To perform rotations and transformations in 3D space.
Explanation: Quaternion math is used to perform rotations and transformations in 3D space. Quaternions are mathematical objects that can be used to represent 3D rotations, and are more efficient and accurate than other methods such as Euler angles.

Distance calculations:
d = √((x2 - x1)^2 + (y2 - y1)^2 + (z2 - z1)^2)
Purpose: To calculate the distance between two points in 3D space.
Explanation: The distance formula is used to calculate the distance between two points in 3D space, which is necessary for rendering the stars and planets in the correct positions.

Orbit calculations:
major = planet.semiMajorAxisLys.value.quantity
minor = planet.semiMinorAxisLys.value.quantity
focusOffset = Math.sqrt(Math.pow(major / 2, 2) - Math.pow(minor / 2, 2))
planetPos = -major + focusOffset
Purpose: To calculate the position of a planet in its orbit around its star.
Explanation: The orbit calculations are used to calculate the position of a planet in its orbit around its star. The semi-major axis and semi-minor axis of the planet's orbit are used to calculate the focus offset, which is then used to calculate the position of the planet.

Ellipse calculations:
ellipse = new THREE.EllipseCurve(focusOffset, 0, major, minor, 0, 2.0 * Math.PI, false)
ellipsePath = new THREE.CurvePath()
ellipsePath.add(ellipse)
ellipseGeometry = ellipsePath.createPointsGeometry(100)
ellipseGeometry.computeTangents()
Purpose: To create an ellipse that represents the orbit of a planet around its star.
Explanation: The ellipse calculations are used to create an ellipse that represents the
