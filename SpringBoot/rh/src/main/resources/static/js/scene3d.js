import * as THREE from "https://unpkg.com/three@0.164.1/build/three.module.js";

const canvas = document.getElementById("scene3d");
const reduceMotion = window.matchMedia("(prefers-reduced-motion: reduce)").matches;

if (canvas && !reduceMotion) {
    const renderer = new THREE.WebGLRenderer({
        canvas,
        alpha: true,
        antialias: true
    });

    renderer.setPixelRatio(Math.min(window.devicePixelRatio || 1, 1.8));
    renderer.setSize(window.innerWidth, window.innerHeight);

    const scene = new THREE.Scene();
    const camera = new THREE.PerspectiveCamera(48, window.innerWidth / window.innerHeight, 0.1, 100);
    camera.position.set(0, 0, 8.5);

    const ambientLight = new THREE.AmbientLight(0xffffff, 0.66);
    scene.add(ambientLight);

    const keyLight = new THREE.DirectionalLight(0xffa94d, 1.3);
    keyLight.position.set(5, 6, 6);
    scene.add(keyLight);

    const fillLight = new THREE.DirectionalLight(0x00b4d8, 1.1);
    fillLight.position.set(-5, -3, 4);
    scene.add(fillLight);

    const coreGroup = new THREE.Group();
    scene.add(coreGroup);

    const knot = new THREE.Mesh(
        new THREE.TorusKnotGeometry(1.4, 0.44, 240, 20),
        new THREE.MeshPhysicalMaterial({
            color: 0xff8f1f,
            metalness: 0.45,
            roughness: 0.25,
            clearcoat: 1,
            clearcoatRoughness: 0.12,
            emissive: 0x111111
        })
    );
    knot.position.x = -1.2;
    coreGroup.add(knot);

    const crystal = new THREE.Mesh(
        new THREE.IcosahedronGeometry(1.1, 1),
        new THREE.MeshPhysicalMaterial({
            color: 0x00a8a8,
            transmission: 0.35,
            thickness: 1.2,
            metalness: 0.1,
            roughness: 0.05,
            clearcoat: 1
        })
    );
    crystal.position.x = 1.8;
    coreGroup.add(crystal);

    const starCount = 900;
    const stars = new Float32Array(starCount * 3);
    for (let i = 0; i < starCount; i += 1) {
        const i3 = i * 3;
        stars[i3] = (Math.random() - 0.5) * 22;
        stars[i3 + 1] = (Math.random() - 0.5) * 15;
        stars[i3 + 2] = (Math.random() - 0.5) * 14;
    }

    const starGeometry = new THREE.BufferGeometry();
    starGeometry.setAttribute("position", new THREE.BufferAttribute(stars, 3));
    const starField = new THREE.Points(
        starGeometry,
        new THREE.PointsMaterial({
            color: 0xffffff,
            size: 0.028,
            transparent: true,
            opacity: 0.55
        })
    );
    scene.add(starField);

    const pointer = { x: 0, y: 0 };
    window.addEventListener("pointermove", (event) => {
        pointer.x = (event.clientX / window.innerWidth) * 2 - 1;
        pointer.y = -((event.clientY / window.innerHeight) * 2 - 1);
    });

    window.addEventListener("resize", () => {
        camera.aspect = window.innerWidth / window.innerHeight;
        camera.updateProjectionMatrix();
        renderer.setSize(window.innerWidth, window.innerHeight);
    });

    const clock = new THREE.Clock();
    const animate = () => {
        const t = clock.getElapsedTime();

        knot.rotation.x = t * 0.36;
        knot.rotation.y = t * 0.52;

        crystal.rotation.x = -t * 0.25;
        crystal.rotation.y = -t * 0.42;
        crystal.position.y = Math.sin(t * 1.4) * 0.2;

        coreGroup.rotation.y += (pointer.x * 0.33 - coreGroup.rotation.y) * 0.04;
        coreGroup.rotation.x += (pointer.y * 0.18 - coreGroup.rotation.x) * 0.04;

        starField.rotation.y = t * 0.02;
        starField.rotation.x = t * 0.01;

        renderer.render(scene, camera);
        requestAnimationFrame(animate);
    };

    animate();
}
